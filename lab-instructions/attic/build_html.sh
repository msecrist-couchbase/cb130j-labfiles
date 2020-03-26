HTML_TARGET_DIR=target/html
STYLESHEET=adoc-riak.css

function genAgenda() {
    
}

# First, clean up the target folder
rm -rf $HTML_TARGET_DIR

# Create fresh target folder and pre-populate with stylesheets
mkdir -p $HTML_TARGET_DIR
cp -r stylesheets $HTML_TARGET_DIR

for file in *.adoc;do
  asciidoctor -a toc=left -a stylesheet=$STYLESHEET -a stylesdir=stylesheets -a linkcss -T ./html-custom -D $HTML_TARGET_DIR $file
done
