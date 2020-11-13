 #!/bin/bash

rm -f *_drawio.pdf;

/usr/bin/find ./ -name "*.drawio" -exec drawio --crop -x -o {}.pdf {} \;

find . -name '*drawio.pdf' -exec bash -c ' mv $0 ${0/.drawio.pdf/_drawio.pdf}' {} \;