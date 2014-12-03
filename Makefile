JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
		$(JC) $(JFLAGS) $*.java

CLASSES = \
		Computer.java \
		Player.java \
		Ball.java \
		BlackHole.java \
		Game.java \
		HipPong.java

default: classes

classes: $(CLASSES:.java=.class)

clean: 
		$(RM) *.class
