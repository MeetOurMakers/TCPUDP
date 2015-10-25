# Set your entry point of your java app:
ENTRY_POINT = src
RES_DIR = no
SOURCE_FILES = \
com/tools/SupTools.java \
com/tcp/TCPServer.java \
com/tcp/TCPClient.java \
com/udp/UDPServer.java \
com/udp/UDPClient.java

# Set your java compiler here:
JAVAC = javac
JFLAGS = -encoding UTF-8 

vpath %.class bin
vpath %.java src

# show help message by default
Default:
	@echo "make new: new project, create src, bin, res dirs."
	@echo "make build: build project."
	@echo "make clean: clear classes generated."
	@echo "make tcpserver/tcpclient/udpserver/udpclient: run specific app."

build: $(SOURCE_FILES:.java=.class)

# pattern rule
%.class: %.java
	$(JAVAC) -cp bin -d bin $(JFLAGS) $<


.PHONY: new clean run jar

new:
ifeq ($(RES_DIR),yes)
	mkdir -pv src bin res
else
	mkdir -pv src bin
endif

clean:
	rm -frv bin/*

udpserver:
	java -cp bin com/udp/UDPServer
udpclient:
	java -cp bin com/udp/UDPClient
tcpserver:
	java -cp bin com/tcp/TCPServer
tcpclient:
	java -cp bin com/tcp/TCPClient
