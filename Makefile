### VARIABLES ###

JC = javac
JCFLAGS = -encoding UTF-8 -implicit:none
JVM = java

SRCDIR = ./src
OUTDIR = ./out
DOCDIR = ./doc
LIBDIR = .:./lib/mariadb-client.jar
OFILES = $(subst src/,out/,$(subst .java,.class,$(shell find $(SRCDIR)/ -name *.java)))

### REGLES ESSENTIELLES ###

$(OUTDIR)/%.class : $(SRCDIR)/%.java
	@mkdir -p $(@D)
	${JC} ${JCFLAGS} -cp $(SRCDIR) -d $(@D) $<

$(OUTDIR)/Main.class : $(OFILES)

# Règle pour lancer Main.class
run : $(OUTDIR)/Main.class
	$(JVM) -cp "$(OUTDIR):$(LIBDIR)" Main

### REGLES OPTIONNELLES ###

clean :
	-rm -rf $(OUTDIR)
	-rm -rf $(DOCDIR)

mrproper : clean $(OUTDIR)/Main.class

doc :
	javadoc -d $(DOCDIR) $(SRCDIR)/*.java
 
seedoc :
	firefox $(DOCDIR)/index.html &

### BUTS FACTICES ###

.PHONY : play create clean mrproper makerun

### FIN ###
