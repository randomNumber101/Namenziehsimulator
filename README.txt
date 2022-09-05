Welcome! 
This tool enables you to create a cyclic dependency graph of a list of users and send each user a customizable e-mail.

What you will need:
     - a list of all users with according e-mail adresses written in a .csv file in the following format:
	name1, e-mail1, ... (other attributes)
	name2, e-mail2, ... (other attributes)
	name3, e-mail3, ... (other attributes)

 	Notice that not all users automatically participate in the game. You will first have to add them.

    - an e-mail template .txt file
	This text will be sent to each participant.
	 You may use special key words in the .txt file that will be replaced with user-dependant information:
		#u_name	 	will be replaced by		the users' name
		#v_name		will be replaced by		the users' victims' name
		#k_name		will be replaced by		the users' killers' name
		#u_list		will be replaced by		a list of all users alphabetically sorted

     - a settings file
	This file includes all the paths and information the tool will need.
	Just look into the example settings file to understand how it works.


How to use the tool:
	0. Start it!
	1. Set up the settings file by using 'setSettings -path_to_settings_file'
	2. Add the desired users to the list of participants by using 'add -name'
	3. Check if everything is alright by using 'status' 
		This will show the participants' names only.
		You may use 'status -victims' to also see everyones' victim
		(You may also remove participantes by using 'removeUser -name')
	4. Shuffle the victims by using 'shuffle'
	5. Check if the mail will be sent correctly by using 'sendNotifications'
		This will not send the notifications yet, but only show you how the e-mail would look like.
		The users' name will be replaced by 'dummy'.
	6. Finally send the notifications for real by using 'sendNotifications -r'
	7. For further information use 'help' or 'help -command_name'
	