### VARIABLES ###

JC = javac
JCFLAGS = -encoding UTF-8 -implicit:none
JVM = java
MANIFEST = ${SRCDIR}/manifest/manifest.txt
PKG = fr/iutfbleau/dick/siuda/paysages
OUTFILE = ${OUTDIR}/fr.iutfbleau.dick.siuda.paysages.jar
SRCDIR = src
IMGDIR = res
OUTDIR = out
OUTJAR = ${OUTDIR}/fr.iutfbleau.dick.siuda.paysages.jar
DOCDIR = ./doc
LIBDIR = ./lib/mariadb-client.jar
OFILES = $(subst src/,out/,$(subst .java,.class,$(shell find $(SRCDIR)/${PKG}/ -name *.java)))

### REGLES ESSENTIELLES ###

# Compilation des fichiers .java en .class
$(OUTDIR)/${PKG}/%.class : $(SRCDIR)/${PKG}/%.java
	@mkdir -p $(@D)
	${JC} ${JCFLAGS} -cp $(SRCDIR) -d $(OUTDIR) $<

# Compilation de tous les fichiers nécessaires sans redéfinir $(OUTDIR)/Main.class
all: $(OFILES)

${OUTJAR} : all
	jar -cfm ${OUTFILE} ${MANIFEST} -C ${OUTDIR} . -C ${SRCDIR} ${IMGDIR}
	cp ${LIBDIR} ${OUTDIR}

# Règle pour lancer Main.class
run: ${OUTJAR}
	$(JVM) -jar ${OUTJAR}

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
