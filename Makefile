SOURCEDIR := src/main
TARGETDIR := target/generated-sources/antlr4

antlr4 = java org.antlr.v4.Tool
grun = java org.antlr.v4.gui.TestRig
SRCFILES = $(SOURCEDIR)/antlr4/main.java $(SOURCEDIR)/antlr4/AST.java
GENERATED = $(TARGETDIR)/hardwareListener.java $(TARGETDIR)/hardwareBaseListener.java $(TARGETDIR)/hardwareParser.java $(TARGETDIR)/hardwareLexer.java $(TARGETDIR)/hardwareBaseVisitor.java $(TARGETDIR)/hardwareVisitor.java
INPUTFILE = $(SOURCEDIR)/resources/03-trafiklys.hw

all:	
	make test

tree:
	make grun

main.class:	$(SRCFILES) $(GENERATED) $(SOURCEDIR)/antlr4/hardware.g4
	javac -d $(TARGETDIR) $(SRCFILES) $(GENERATED)

hardwareLexer.java:	$(SOURCEDIR)/antlr4/hardware.g4
	$(antlr4) -visitor $(SOURCEDIR)/antlr4/hardware.g4 -o $(TARGETDIR)

hardwareLexer.class:	hardwareLexer.java
	javac -d $(TARGETDIR) $(GENERATED)

grun:	hardwareLexer.class $(SOURCEDIR)/resources/03-trafiklys.hw
	cd $(TARGETDIR) && $(grun) hardware start -gui -tokens ../../../$(INPUTFILE)

test:	hardwareLexer.class main.class $(SOURCEDIR)/antlr4/hardware.g4 $(INPUTFILE)
	cd $(TARGETDIR) && java main ../../../$(INPUTFILE)