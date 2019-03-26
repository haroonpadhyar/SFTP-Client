@echo off

@rem Host and user/password
SET HOST=n16339
SET USER=admin
SET PWD=Aa123456

@rem File name
SET FOLDER=%date:~-4%%date:~4,2%%date:~7,2%
SET LOG_FILE=SFTP_LOG.txt
SET SSH_LOG_FILE=SSH_SFTP_LOG.txt
SET SSH_SCRIPT_FILE=SFTP_SCRIPT.txt

@rem Create Directory
if not exist %FOLDER% mkdir %FOLDER%

@rem Create SFTP script file.
ECHO ls > %FOLDER%\%SSH_SCRIPT_FILE%
ECHO get -r "All Files" %FOLDER% >> %FOLDER%\%SSH_SCRIPT_FILE%
ECHO quit >> %FOLDER%\%SSH_SCRIPT_FILE%
ECHO close >> %FOLDER%\%SSH_SCRIPT_FILE%

@rem Open SFTP connection.
@rem psftp %USER%@%HOST% -pw %PWD% -v -be -b %FOLDER%\%SSH_SCRIPT_FILE% -bc >> %FOLDER%\%LOG_FILE% -sshlog %FOLDER%\%SSH_LOG_FILE%
psftp %USER%@%HOST% -pw %PWD% -v -be -b %FOLDER%\%SSH_SCRIPT_FILE% -bc >> %FOLDER%\%LOG_FILE% -batch

@rem Delete SFTP script file.
del %FOLDER%\%SSH_SCRIPT_FILE%

