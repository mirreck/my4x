#!/usr/local/bin/fontforge
# Convert this SVG to all other web types
Print("Opening "+$1+" Name="+$2 +"path="+$3);
Open($1);
Print("Saving "+$2+"-webfont.ttf");
Generate("../webapp/resources/lib/"+$3+"/"+$2+"-webfont.ttf");
Print("Saving "+$2+"-webfont.otf");
Generate("../webapp/resources/lib/"+$3+"/"+$2+"-webfont.otf");
Print("Saving "+$2+"-webfont.woff");
Generate("../webapp/resources/lib/"+$3+"/"+$2+"-webfont.woff");
Print("Saving "+$2+"-webfont.svg");
Generate("../webapp/resources/lib/"+$3+"/"+$2+"-webfont.svg");
Quit(0); 
