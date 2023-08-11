#Nesan Naidoo
#01/08/2023

JAVAC=javac


SRCDIR=src
BINDIR=bin

SOURCES := $(wildcard $(SRCDIR)/MonteCarloMini/*.java)

CLASSES := $(SOURCES:$(SRCDIR)/%.java=$(BINDIR)/%.class)

all: $(CLASSES)

$(BINDIR)/%.class: $(SRCDIR)/%.java
	$(JAVAC) -d bin -cp -Xlint src/MonteCarloMini/*.java

clean:
	rm -rf $(BINDIR)

runS:
		java -Xmx6g -cp $(BINDIR) MonteCarloMini.MonteCarloMinimization 1000 1000 0 1000 0 1000 32.5
runP:
		java -Xmx6g -cp $(BINDIR) MonteCarloMini.MonteCarloMinimizationParallel 1000 1000 0 1000 0 1000 32.5