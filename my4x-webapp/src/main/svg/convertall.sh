#!/usr/local/bin/fontforge
# Convert this SVG to all other web types
Print("Opening "+$1);
Open($1);
Print("Saving fontextend-webfont.ttf");
Generate("../webapp/resources/lib/font-extend/font/font-extend-webfont.ttf");
Print("Saving fontextend-webfont.otf");
Generate("../webapp/resources/lib/font-extend/font/font-extend-webfont.otf");
Print("Saving fontextend-webfont.woff");
Generate("../webapp/resources/lib/font-extend/font/font-extend-webfont.woff");
Print("Saving fontextend-webfont.svg");
Generate("../webapp/resources/lib/font-extend/font/font-extend-webfont.svg");
Quit(0); 
