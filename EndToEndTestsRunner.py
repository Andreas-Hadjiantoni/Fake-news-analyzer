from threading import BoundedSemaphore, Lock, Thread
import threading
import time
import subprocess
import os
tests = "./tests.txt"

'''
IMPORTANT:
==========
Please execute with python 2.7
'''

def parseLines(lines):
    tests=[]
    outputs=[]
    readingTest=False
    readingOutput=False
    firstOutputLine=True

    for i, line in enumerate(lines):
        if line == 'test:\n':
            readingTest=True;
            readingOutput=False
        if line=="expectedOutput:\n":
            readingTest=False;
            readingOutput=True
            firstOutputLine=True
        if (line != "test:\n" and line != "expectedOutput:\n"):
            if readingTest:
                tests.append(line)
            if readingOutput:
                if firstOutputLine:
                    outputs.append([line])
                    firstOutputLine=False
                else:
                    outputs[-1].append(line)

    return tests, outputs

launchCmd = "mvn"
option1 = "compile"
option2 = "exec:java"
option3 = "-Dexec.mainClass=com.TweeterAnalytics.Main"

process = subprocess.Popen([launchCmd, option1, option2, option3],
                         stdin=subprocess.PIPE,
                         stdout=subprocess.PIPE,
                         stderr=subprocess.PIPE)

fd = open(tests, "rb")

lines = fd.readlines()

tests,outputs = parseLines(lines)

firstTime=1
testIndex = 0
lineIndex = 0
for i, test in enumerate(tests):

    if (firstTime==1):
        for line in iter(process.stdout.readline, b''):
            if line == "[INFO] --- exec-maven-plugin:1.6.0:java (default-cli) @ indProject ---\n":
                break;
        firstTime=0

    process.stdin.write(test)
    process.stdin.flush()

    for line in iter(process.stdout.readline, b''):
        if (line[:5] == ">>>> "):
            line = line[5:]
        if not outputs[testIndex][lineIndex] == line:
            if ( not (line == "\n" and outputs[testIndex][lineIndex]=="empty\n") ):
                print("test " + str(testIndex) + " failed")
                exit(1)

        if (len(outputs[testIndex]) == lineIndex + 1):
            lineIndex =  0
            testIndex += 1
            print("test " + str(testIndex) + " passed")
            break
        else:
            lineIndex += 1


process.communicate()
