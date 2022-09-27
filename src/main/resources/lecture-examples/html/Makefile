antlr4 = java org.antlr.v4.Tool
grun = java org.antlr.v4.gui.TestRig
SRCFILES = main.java
GENERATED = htmlListener.java htmlBaseListener.java htmlParser.java htmlLexer.java

all:	
	make grun

htmlLexer.java:	html.g4
	$(antlr4) html.g4

htmlLexer.class:	htmlLexer.java
	javac $(GENERATED)

grun:	htmlLexer.class example.html
	$(grun) html start -gui -tokens example.html
