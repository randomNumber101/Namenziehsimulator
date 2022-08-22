import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.config.properties.APKeys
import com.atlassian.jira.issue.Issue
import com.atlassian.jira.issue.IssueManager
import com.atlassian.jira.issue.fields.CustomField
import com.atlassian.jira.issue.link.IssueLinkManager
import groovy.transform.Field

@Field Long linkType = 10500L
@Field IssueManager issueManager = ComponentAccessor.issueManager
@Field IssueLinkManager issueLinkManager = ComponentAccessor.issueLinkManager

def log(String message) {
    logger.warn "POWERCLONE: ${message}"
}

def createIssueLink(Long sourceId, Long destinationId) {
    issueLinkManager.createIssueLink(sourceId, destinationId, linkType, null, currentUser)
}

def getIssueLink(Issue issue) {
    def jiraBaseUrl = ComponentAccessor.applicationProperties.getString APKeys.JIRA_BASEURL
    "${jiraBaseUrl}/browse/${issue.key}"
}

def isValidIssueTypeToClone(Issue issue) {
    def blockedTypes = [
            "10200", // Eckpunkt ID
            "10700", // Projektstatus ID
            "10201", // Meilenstein ID
            "10101"  // Unteraufgabe ID
    ]

    !blockedTypes.contains(issue.issueTypeId)
}

Issue cloneIssueBody(Issue issue, Long projectId) {
    def newIssue = ComponentAccessor.issueFactory.cloneIssueWithAllFields issue
    if (projectId != null) newIssue.projectId = projectId

    def clonedIssue = issueManager.createIssueObject(issue.reporter, newIssue)

    log "Cloned body: ${issue.key} -> ${clonedIssue.key}"
    clonedIssue
}

def cloneIssue(Issue issue, Issue clonedIssue, Long projectId) {
    issueLinkManager.getOutwardLinks(issue.id).each {
        def originalIssue = it.destinationObject
        def childIssue = cloneIssueBody(originalIssue, projectId)

        log "Child Link: ${clonedIssue.key} -> ${childIssue.key}"
        createIssueLink(clonedIssue.id, childIssue.id)

        cloneIssue(originalIssue, childIssue, projectId)
    }
}

List<Issue> getParentIssues(String parentKey) {
    log "Zielvorgang: ${parentKey}"

    List<Issue> parentIssues = []

    if (parentKey == null || parentKey == "Empty") {
        issueLinkManager.getLinkCollection(issue, currentUser).allIssues.each {
            def link = issueLinkManager.getIssueLink(it.id, issue.id, linkType)
            if (link != null) parentIssues.add link.sourceObject
        }
    } else {
        def parentIssue = issueManager.getIssueObject parentKey
        parentIssues.add parentIssue
    }

    parentIssues
}

def restoreFields(CustomField zielvorgangField) {
    issue.setCustomFieldValue(zielvorgangField, null)

    def changes = ComponentAccessor.changeHistoryManager.getChangeItemsForField(issue, "summary")
    def originalSummary = changes?.last()
    if (originalSummary != null) issue.summary = originalSummary.toString
}

log "=== POWERCLONE START ==="

if (isValidIssueTypeToClone(issue)) {
    CustomField field = ComponentAccessor.customFieldManager.getCustomFieldObjectsByName("Zielvorgang").first()
    String parentKey = issue.getCustomFieldValue field

    def parentIssues = getParentIssues parentKey
    def projectId = parentIssues?.find()?.projectId

    def clonedIssue = cloneIssueBody(issue, projectId)
    cloneIssue(issue, clonedIssue, projectId)

    parentIssues.each {
        log "Parent Link: ${it.key} -> ${clonedIssue.key}"
        createIssueLink(it.id, clonedIssue.id)
    }

    restoreFields field

    log "${getIssueLink clonedIssue}"
} else {
    log "Vordefinierte Typen Eckpunkte, Projektstatus und Meilensteine und Unteraufgaben dьrfen nicht geklont werden."
}