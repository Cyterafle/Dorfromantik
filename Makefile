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

# Compilation des fichiers .java en .class
$(OUTDIR)/%.class : $(SRCDIR)/%.java
	@mkdir -p $(@D)
	${JC} ${JCFLAGS} -cp $(SRCDIR) -d $(@D) $<

# Compilation de tous les fichiers nécessaires sans redéfinir $(OUTDIR)/Main.class
all: $(OFILES)

# Règle pour lancer Main.class
run: all
	$(JVM) -cp "$(OUTDIR):$(LIBDIR)" Main

### REGLES OPTIONNELLES ###

clean:
	-rm -rf $(OUTDIR)
	-rm -rf $(DOCDIR)

mrproper: clean all

doc:
	javadoc -d $(DOCDIR) $(SRCDIR)/*.java

seedoc:
	firefox $(DOCDIR)/index.html &

### BUTS FACTICES ###

.PHONY: run clean mrproper doc seedoc

### FIN ###
