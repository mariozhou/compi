package Main;
import static Main.Tokens.*;
%%
%class Lexer
%type Tokens
%column
%line
LETTER =[A-Za-zÑñ_]+
NUM =[0-9]
WhiteSpace=[\t\r\n ]+
%{
    public String lexeme;
    public int linea;
    public int columna;
%}
%%

/* Espacios en blanco */
{WhiteSpace} {/*Ignore*/}

/* Comentarios */
( "#"(.)* ) {/*Ignore*/}

/* Signos */

( "\." ) {lexeme=yytext(); linea = yyline; columna = yycolumn; Main.listaCompLexico.add("Punto"); Main.listaLexemas.add(lexeme); Main.listaLineaLexemas.add(linea+1); return Punto;}
( "\," ) {lexeme=yytext(); linea = yyline; columna = yycolumn; Main.listaCompLexico.add("Separador"); Main.listaLexemas.add(lexeme); Main.listaLineaLexemas.add(linea+1); return Separador;}
( "\;" ) {lexeme=yytext(); linea = yyline; columna = yycolumn; Main.listaCompLexico.add("Delimitador"); Main.listaLexemas.add(lexeme); Main.listaLineaLexemas.add(linea+1); return Delimitador;}
( "\:" ) {lexeme=yytext(); linea = yyline; columna = yycolumn; Main.listaCompLexico.add("Dos_puntos"); Main.listaLexemas.add(lexeme); Main.listaLineaLexemas.add(linea+1); return Dos_puntos;}

/* Comillas Simples*/
( "\'" ) {lexeme=yytext(); linea = yyline; columna = yycolumn; Main.listaCompLexico.add("Comilla simple"); Main.listaLexemas.add(lexeme); Main.listaLineaLexemas.add(linea+1); return Comilla_simple;}
( "\'" ) [^';]+ ( "\'" ) | ( "\'" "\'" )  {lexeme=yytext(); linea = yyline; columna = yycolumn; Main.listaCompLexico.add("Cadena"); Main.listaLexemas.add(lexeme); Main.listaLineaLexemas.add(linea+1); return Cadena;}


/* ----------------> Palabras reservadas <-----------------------*/

( "Import" | "RecordTime" | "IsCamaraOff" | "Micro" | "Detener" | "Retroceder" | "Avanzar" | "IsSound" | "Whistle" | "Capture" | "Receive" | "DeviceType" | "Enable" | "False" |"True" | "FaceCheck" | "IsOff" | "SmartCamera" | "Program" | "This" | "IsOn" | "Doorbell" | "Int" | "Text" | "Float" | "Bool" | "Display" | "For" | "While" | "If" | "Else" | "When" | "Default" | "Home" | "Initialize" | "PortA" | "PortB" | "PortC" | "PortD" | "Enable" | "Room" | "New" |"Temp" | "GetTemp" | "Ac" | "Set" | "Start" | "Shutdown" | "Light" | "Off" | "On" | "LightRGB" | "LightMode" | "Color" | "Door" | "Open" | "Close" | "Lock" | "Unlock" | "Window" | "IsOpen" | "IsClose" | "IsLock" |"IsUnlock" | "UnlockAt" | "LockAt" | "Camera" | "Record" | "StopRec" | "Move" | "IsMove" | "Alarm" | "Device" | "IsIn" | "IsOut" ) {lexeme=yytext(); linea = yyline; columna = yycolumn; Main.listaCompLexico.add("Palabra reservada"); Main.listaLexemas.add(lexeme); Main.listaLineaLexemas.add(linea+1); return Palabra_reservada;}

/* ------- | Op. Relacionales | ------- */

( "=" )  {lexeme=yytext(); linea = yyline; columna = yycolumn; Main.listaCompLexico.add("Op. Asignación"); Main.listaLexemas.add(lexeme); Main.listaLineaLexemas.add(linea+1); return Asignacion;}
( "=>" )  {lexeme=yytext(); linea = yyline; columna = yycolumn; Main.listaCompLexico.add("Op. Arrow"); Main.listaLexemas.add(lexeme); Main.listaLineaLexemas.add(linea+1); return AsignacionArrow;}
( "==" ) {lexeme=yytext(); linea = yyline; columna = yycolumn; Main.listaCompLexico.add("Op. Relacional"); Main.listaLexemas.add(lexeme); Main.listaLineaLexemas.add(linea+1); return Igual;}
( "<" )  {lexeme=yytext(); linea = yyline; columna = yycolumn; Main.listaCompLexico.add("Op. Relacional"); Main.listaLexemas.add(lexeme); Main.listaLineaLexemas.add(linea+1); return Menor;}
( ">" )  {lexeme=yytext(); linea = yyline; columna = yycolumn; Main.listaCompLexico.add("Op. Relacional"); Main.listaLexemas.add(lexeme); Main.listaLineaLexemas.add(linea+1); return Mayor;}
( ">=" ) {lexeme=yytext(); linea = yyline; columna = yycolumn; Main.listaCompLexico.add("Op. Relacional"); Main.listaLexemas.add(lexeme); Main.listaLineaLexemas.add(linea+1); return Mayor_igual;}
( ">=" ) {lexeme=yytext(); linea = yyline; columna = yycolumn; Main.listaCompLexico.add("Op. Relacional"); Main.listaLexemas.add(lexeme); Main.listaLineaLexemas.add(linea+1); return Menor_igual;}
( "!=" ) {lexeme=yytext(); linea = yyline; columna = yycolumn; Main.listaCompLexico.add("Op. Relacional"); Main.listaLexemas.add(lexeme); Main.listaLineaLexemas.add(linea+1); return Diferente;}

/* ------- | Op. Aritmeticos | ------- */

( "+" ) {lexeme=yytext(); linea = yyline; columna = yycolumn; Main.listaCompLexico.add("Op. Aritmetico"); Main.listaLexemas.add(lexeme); Main.listaLineaLexemas.add(linea+1); return Suma;}
( "-" ) {lexeme=yytext(); linea = yyline; columna = yycolumn; Main.listaCompLexico.add("Op. Aritmetico"); Main.listaLexemas.add(lexeme); Main.listaLineaLexemas.add(linea+1); return Resta;}
( "*" ) {lexeme=yytext(); linea = yyline; columna = yycolumn; Main.listaCompLexico.add("Op. Aritmetico"); Main.listaLexemas.add(lexeme); Main.listaLineaLexemas.add(linea+1); return Multiplicacion;}
( "/" ) {lexeme=yytext(); linea = yyline; columna = yycolumn; Main.listaCompLexico.add("Op. Aritmetico"); Main.listaLexemas.add(lexeme); Main.listaLineaLexemas.add(linea+1); return Division;}

/* ------- | Op. Lógicos | ------- */

( "&&" ) {lexeme=yytext(); linea = yyline; columna = yycolumn; Main.listaCompLexico.add("Op. Lógico"); Main.listaLexemas.add(lexeme); Main.listaLineaLexemas.add(linea+1); return And;}
( "||" ) {lexeme=yytext(); linea = yyline; columna = yycolumn; Main.listaCompLexico.add("Op. Lógico"); Main.listaLexemas.add(lexeme); Main.listaLineaLexemas.add(linea+1); return Or;}

/* ------- | Op. Incrementales | ------- */

( "--" ) {lexeme=yytext(); linea = yyline; columna = yycolumn; Main.listaCompLexico.add("Op. Decremental"); Main.listaLexemas.add(lexeme); Main.listaLineaLexemas.add(linea+1); return Dec;}
( "++" ) {lexeme=yytext(); linea = yyline; columna = yycolumn; Main.listaCompLexico.add("Op. Incremental"); Main.listaLexemas.add(lexeme); Main.listaLineaLexemas.add(linea+1); return Inc;}

/* ------- | Op. Agrupación | ------- */

( "(" ) {lexeme=yytext(); linea = yyline; columna = yycolumn; Main.listaCompLexico.add("Op. Agrupación"); Main.listaLexemas.add(lexeme); Main.listaLineaLexemas.add(linea+1); return Parentesis_izq;}
( ")" ) {lexeme=yytext(); linea = yyline; columna = yycolumn; Main.listaCompLexico.add("Op. Agrupación"); Main.listaLexemas.add(lexeme); Main.listaLineaLexemas.add(linea+1); return Parentesis_der;}
( "[" ) {lexeme=yytext(); linea = yyline; columna = yycolumn; Main.listaCompLexico.add("Op. Agrupación"); Main.listaLexemas.add(lexeme); Main.listaLineaLexemas.add(linea+1); return Corchete_izq;}
( "]" ) {lexeme=yytext(); linea = yyline; columna = yycolumn; Main.listaCompLexico.add("Op. Agrupación"); Main.listaLexemas.add(lexeme); Main.listaLineaLexemas.add(linea+1); return Corchete_der;}
( "{" ) {lexeme=yytext(); linea = yyline; columna = yycolumn; Main.listaCompLexico.add("Op. Agrupación"); Main.listaLexemas.add(lexeme); Main.listaLineaLexemas.add(linea+1); return Llave_izq;}
( "}" ) {lexeme=yytext(); linea = yyline; columna = yycolumn; Main.listaCompLexico.add("Op. Agrupación"); Main.listaLexemas.add(lexeme); Main.listaLineaLexemas.add(linea+1); return Llave_der;}

/* Identificador */

{LETTER}({LETTER}|{NUM})* {lexeme=yytext(); linea = yyline; columna = yycolumn; Main.listaCompLexico.add("Identificador"); Main.listaLexemas.add(lexeme); Main.listaLineaLexemas.add(linea+1); return Identificador;}

/* Numero */

["+""-"]? {NUM}+                                                                                                            {lexeme=yytext(); linea = yyline; columna = yycolumn; Main.listaCompLexico.add("Número entero"); Main.listaLexemas.add(lexeme); Main.listaLineaLexemas.add(linea+1); return Numero_Entero;}
["+""-"]? {NUM}+"."{NUM}+                                                                                                   {lexeme=yytext(); linea = yyline; columna = yycolumn; Main.listaCompLexico.add("Número flotante"); Main.listaLexemas.add(lexeme); Main.listaLineaLexemas.add(linea+1); return Numero_Flotante;}
["+""-"]? {NUM}+[eE]["+""-"]?{NUM}*                                                                                         {lexeme=yytext(); linea = yyline; columna = yycolumn; Main.listaCompLexico.add("Número euler"); Main.listaLexemas.add(lexeme); Main.listaLineaLexemas.add(linea+1); return Numero_Euler;}
["+""-"]? ({NUM}+"."{NUM}*[eE]["+""-"]?{NUM}* | {NUM}+[eE]{NUM}*"."{NUM}+ |{NUM}+[eE]{NUM}*"."{NUM}*[eE]["+""-"]?{NUM}*)    {lexeme=yytext(); linea = yyline; columna = yycolumn; Main.listaCompLexico.add("Número euler flotante"); Main.listaLexemas.add(lexeme); Main.listaLineaLexemas.add(linea+1); return Numero_Euler_Flotante;}

/* Error de analisis */
({NUM}+{LETTER}+)+ {lexeme=yytext(); linea = yyline; columna = yycolumn; Main.listaCompLexico.add("Error léxico"); Main.listaLexemas.add(lexeme); Main.listaLineaLexemas.add(linea+1); Main.listaErrores.add("> Linea ( " + (linea+1) + " ) - Error léxico - e001 - Cadena no válida -> " + lexeme + "\n"); Main.error = true; return error;}

// Error numeros enteros
{NUM}({NUM}|{LETTER}|[^a-zA-ZÑñ_0-9\!\,\.\=\+\-\*\/\;\[\]\(\)\{\}\<\>\"\'\t\r\n ])* ({LETTER}|[^a-zA-ZÑñ_0-9\!\,\.\=\+\-\*\/\;\[\]\(\)\{\}\<\>\"\'\t\r\n ]) ({NUM}|{LETTER}|[^a-zA-ZÑñ_0-9\!\,\.\=\+\-\*\/\;\:\[\]\(\)\{\}\<\>\"\'\t\r\n ])* {lexeme=yytext(); linea = yyline; columna = yycolumn; Main.listaCompLexico.add("Error léxico"); Main.listaLexemas.add(lexeme); Main.listaLineaLexemas.add(linea+1); Main.listaErrores.add("> Linea ( "+ (linea+1) +" ) - Error léxico - e001 - Cadena no válida -> " + lexeme + "\n"); Main.error = true; return E1;}

// Error numeros decimales
// ({NUM}+(\.|[\!\,])(\.|[\!\,])+{NUM}+ | (\.|[\!\,])+{NUM}+(\.|[\!\,])+{NUM}+ | (\.|[\!\,])+{NUM}+(\.|[\!\,])+ | (\.|[\!\,])+{NUM}+ | {NUM}+(\.|[\!\,])+ | (\.|[\!\,])+{NUM}+(\.|[\!\,])+{NUM}+(\.|[\!\,])+)+ {lexeme=yytext(); linea = yyline; columna = yycolumn; Main.listaCompLexico.add("Error léxico"); Main.listaLexemas.add(lexeme); Main.listaLineaLexemas.add(linea+1); Main.listaErrores.add("> Linea ( "+ (linea+1) +" ) - Error léxico - e002 - Número decimal no válido -> " + lexeme + "\n"); Main.error = true; return E6;}
(\.)*{NUM}+(\.)+({NUM}*(\.)*|(\.)*{NUM}*)* {lexeme=yytext(); linea = yyline; columna = yycolumn; Main.listaCompLexico.add("Error léxico"); Main.listaLexemas.add(lexeme); Main.listaLineaLexemas.add(linea+1); Main.listaErrores.add("> Linea ( "+ (linea+1) +" ) - Error léxico - e002 - Número decimal no válido -> " + lexeme + "\n"); Main.error = true; return E6;}

// Error simbolo no reconocido
([a-zA-ZÑñ_0-9\!\,\.\=\+\-\*\/\;\[\]\(\)\{\}\<\>\']* [^a-zA-ZÑñ_0-9\!\,\.\=\+\-\*\/\;\[\]\(\)\{\}\<\>\'\t\r\n ]+ [a-zA-ZÑñ_0-9\!\,\.\=\+\-\*\/\;\[\]\(\)\{\}\<\>\']*)+ {lexeme=yytext(); linea = yyline; columna = yycolumn; Main.listaCompLexico.add("Error léxico"); Main.listaLexemas.add(lexeme); Main.listaLineaLexemas.add(linea+1); Main.listaErrores.add("> Linea ( "+ (linea+1) +" ) - Error léxico - e001 - Cadena no válida -> " + lexeme + "\n"); Main.error = true; return E3;}

// Error general
. {lexeme=yytext(); linea = yyline; columna = yycolumn; Main.listaCompLexico.add("Error léxico"); Main.listaLexemas.add(lexeme); Main.listaLineaLexemas.add(linea+1); Main.listaErrores.add("> Linea ( "+ (linea+1) +" ) - Error léxico - e005 - Cadena no válida -> " + lexeme + "\n"); Main.error = true; return E5;}

// [a-zA-ZÑñ_0-9\!\,\.\=\+\-\*\/\;\:\[\]\(\)\{\}\<\>\"\']* [^a-zA-ZÑñ_0-9\!\,\.\=\+\-\*\/\;\:\[\]\(\)\{\}\<\>\"\'\t\r\n ]+ [a-zA-ZÑñ_0-9\!\,\.\=\+\-\*\/\;\:\[\]\(\)\{\}\<\>\"\']* {lexeme=yytext(); linea = yyline; columna = yycolumn; Main.listaCompLexico.add("Error léxico"); Main.listaLexemas.add(lexeme); Main.listaLineaLexemas.add(linea+1); Main.listaErrores.add("> Linea ( "+ (linea+1) +" ) - Error léxico - e001 - Cadena no válida -> " + lexeme + "\n"); Main.error = true; return E3;}