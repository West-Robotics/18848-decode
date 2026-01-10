ROOT_DIR := $(dir $(lastword $MAKEFILE_LIST))
.PHONY: adb sloth teamcode
sloth:
	${ROOT_DIR}/gradlew :TeamCode:deploySloth
adb:
	iwctl station wlp4s0 scan
	iwctl station wlp4s0 connect 18848-RC
	-killall adb
	sleep 1
	adb connect 192.168.43.1
teamcode:
	${ROOT_DIR}/gradlew :TeamCode:removeSlothRemote
	${ROOT_DIR}/gradlew :TeamCode:installDebug
