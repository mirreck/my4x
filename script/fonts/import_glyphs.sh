#!/bin/sh

# proxy if needed
#export http_proxy=http://{login}:{pwd}@{host}:{port}/

# get the category page
#wget –O category.html "http://commons.wikimedia.org/wiki/Category:Single_tengwa_in_SVG"
url="http://commons.wikimedia.org/wiki/Category:Single_certh_in_SVG"
#wget –O category.html $url

# extract all svg URLs
#cat category.html|grep -o '//upload.wikimedia.org/wikipedia/commons/thumb/.*.svg.png' > list.txt
# change to actual svg file URLs
#sed "s/\/thumb//g" list.txt > list.txt.1
#sed "s/\/[0-9]*px.*//g" list.txt.1 > list.txt.2
# download files
# file="list.txt.2"
# rm svg/*.*
# mkdir -p svg
# cd svg
# while IFS= read -r line
# do
	# filename=$(echo $line|grep -o "[^/]*\.svg")
	# id=$(echo $filename|sed 's/\.svg//')
	# wget -o log.txt q–O $filename "http:$line"
	# echo "id: $id" 
	# sed "s/id=\"text2389\"/id=\"$id\"/g" $filename > "$filename.2"
# done <"../$file"
# cd ..


cat head.svg.part > mix.svg
for FILENAME in $(find svg -name "*.svg.2")
do
	echo "file: $FILENAME" 
	tr '\n' ' ' < $FILENAME > $FILENAME.1
	cat $FILENAME.1|grep -o '<path[^>]*>' >> mix.svg
done
cat foot.svg.part >> mix.svg
echo "Done"
