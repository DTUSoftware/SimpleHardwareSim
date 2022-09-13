SOURCEDIR := src/main
TARGETDIR := target/generated-sources/antlr4

antlr4 = java org.antlr.v4.Tool
grun = java org.antlr.v4.gui.TestRig
SRCFILES = main.java
GENERATED = $(TARGETDIR)/hardwareListener.java $(TARGETDIR)/hardwareBaseListener.java $(TARGETDIR)/hardwareParser.java $(TARGETDIR)/hardwareLexer.java

all:	
	make grun

hardwareLexer.java:	$(SOURCEDIR)/antlr4/hardware.g4
	$(antlr4) $(SOURCEDIR)/antlr4/hardware.g4 -o $(TARGETDIR)

hardwareLexer.class:	hardwareLexer.java
	javac -d $(TARGETDIR) $(GENERATED)

grun:	hardwareLexer.class $(SOURCEDIR)/resources/01-hello-world.hw
	cd $(TARGETDIR) && $(grun) hardware start -gui -tokens ../../../$(SOURCEDIR)/resources/01-hello-world.hw
