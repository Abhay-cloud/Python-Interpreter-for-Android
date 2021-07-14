# import important libraries
import sys
from os.path import dirname, join
from com.chaquo.python import Python

# Get data here
def main(CodeAreaData):

    file_dir = str(Python.getPlatform().getApplication().getFilesDir())

    filename = join(dirname(file_dir), 'file.txt')

    try:
        original_stdout = sys.stdout

        sys.stdout = open(filename, 'w', encoding = 'utf8', errors = "ignore")

        exec(CodeAreaData)

        sys.stdout.close()

        sys.stdout = original_stdout
        output = open(filename, 'r').read()


    except Exception as e:
        sys.stdout = original_stdout

        output = e

        # finally return output

    return str(output)


