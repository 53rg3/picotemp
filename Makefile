
CLI_NAME="picotemp"
WORK_FOLDER="target"
BIN_FOLDER="bin"
AUTOCOMPLETE_SH="autocomplete.sh"
CURRENT_DIR=$(CURDIR)

build:
	mvn -e clean package assembly:single
	mkdir --parents $(BIN_FOLDER)
	mv $(WORK_FOLDER)/$(CLI_NAME).jar $(BIN_FOLDER)/$(CLI_NAME).jar
	mv $(WORK_FOLDER)/$(AUTOCOMPLETE_SH) $(BIN_FOLDER)/$(AUTOCOMPLETE_SH)
	@echo "-------------- DONE --------------"
	@echo "Test via:"
	@echo "    java -jar $(CURRENT_DIR)/$(BIN_FOLDER)/$(CLI_NAME).jar"
	@echo "Put into ~/.bashrc:"
	@echo "    alias $(CLI_NAME)=\"java -jar $(CURRENT_DIR)/$(BIN_FOLDER)/$(CLI_NAME).jar\""
	@echo "    source $(CURRENT_DIR)/$(BIN_FOLDER)/$(AUTOCOMPLETE_SH)"
