package Main;
import java_cup.runtime.Symbol;
%%
%class LexerCup
%type java_cup.runtime.Symbol

%cup
%line
%column
%ignorecase
L=[a-zA-Z_]+
D=[0-9]
espacio=[ \s\t\r\n]+
%{
	public Symbol token(int simbolo){
		// Lexema lexema = new Lexema( yytext() );
		return new Symbol(simbolo,yyline,yycolumn,yytext());
	}
	public Symbol token(int simbolo,String componenteLexico){
		// Cup.vars++;
		// Lexema lexema = new Lexema( yytext() );
		return new Symbol(simbolo,yyline,yycolumn,yytext());
	}
%}
%%

/* Signos */

( "\." ) {return new Symbol(sym.Punto, yychar, yyline, yytext());}
( "\," ) {return new Symbol(sym.Coma, yychar, yyline, yytext());}
( "\;" ) {return new Symbol(sym.PComa, yychar, yyline, yytext());}

/* Espacios en blanco */
{espacio} {/*Ignore*/}

/* Salto de linea */
// {NL} {return new Symbol(sym.NL, yychar, yyline, yytext());}

/* Comentarios */
( "#"(.)* ) {/*Ignore*/}

/* Comillas Simples*/
( "\'" ) {return new Symbol(sym.Comillas_simples, yychar, yyline, yytext());}
( "\'" ) [^';]+ ( "\'" ) | ( "\'" "\'" ) {return new Symbol(sym.Cadena, yychar, yyline, yytext());}

/* ----------------> Palabras reservadas <-----------------------*/

/* ------- | Libreria | ------- */
( "\//" )    {return new Symbol(sym.Libreria, yychar, yyline, yytext());}

/* ------- | Esc. Principal | ------- */

[H][o][m][e] {return new Symbol(sym.Home, yychar, yyline, yytext());}
[I][n][i][t][i][a][l][i][z][e] {return new Symbol(sym.Initialize, yychar, yyline, yytext());}

/* ------- | Puertos | ------- */

[P][o][r][t][A] {return new Symbol(sym.PortA, yychar, yyline, yytext());}
[P][o][r][t][B] {return new Symbol(sym.PortB, yychar, yyline, yytext());}
[P][o][r][t][C] {return new Symbol(sym.PortC, yychar, yyline, yytext());}
[P][o][r][t][D] {return new Symbol(sym.PortD, yychar, yyline, yytext());}
[E][n][a][b][l][e] {return new Symbol(sym.Enable, yychar, yyline, yytext());}

/* ------- | Esc. Cuarto | ------- */

[R][o][o][m] {return new Symbol(sym.Room, yychar, yyline, yytext());}

/* ------- | Funciones | ------- */

[N][e][w]                           {return new Symbol(sym.New, yychar, yyline, yytext());}
[T][e][m][p]                        {return new Symbol(sym.Temp, yychar, yyline, yytext());}
[G][e][t][T][e][m][p]               {return new Symbol(sym.GetTemp, yychar, yyline, yytext());}
[A][c]                              {return new Symbol(sym.Ac, yychar, yyline, yytext());}
[S][e][t]                           {return new Symbol(sym.Set, yychar, yyline, yytext());}
[S][t][a][r][t]                     {return new Symbol(sym.Start, yychar, yyline, yytext());}
[S][h][u][t][d][o][w][n]            {return new Symbol(sym.Shutdown, yychar, yyline, yytext());}
[L][i][g][h][t]                     {return new Symbol(sym.Light, yychar, yyline, yytext());}
[O][f][f]                           {return new Symbol(sym.Off, yychar, yyline, yytext());}
[O][n]                              {return new Symbol(sym.On, yychar, yyline, yytext());}
[L][i][g][h][t][R][G][B]            {return new Symbol(sym.LightRGB, yychar, yyline, yytext());}
[L][i][g][h][t][M][o][d][e]         {return new Symbol(sym.LightMode, yychar, yyline, yytext());}
[C][o][l][o][r]                     {return new Symbol(sym.Color, yychar, yyline, yytext());}
[D][o][o][r]                        {return new Symbol(sym.Door, yychar, yyline, yytext());}
[O][p][e][n]                        {return new Symbol(sym.Open, yychar, yyline, yytext());}
[C][l][o][s][e]                     {return new Symbol(sym.Close, yychar, yyline, yytext());}
[L][o][c][k]                        {return new Symbol(sym.Lock, yychar, yyline, yytext());}
[U][n][l][o][c][k]                  {return new Symbol(sym.Unlock, yychar, yyline, yytext());}
[W][i][n][d][o][w]                  {return new Symbol(sym.Window, yychar, yyline, yytext());}
[I][s][O][p][e][n]                  {return new Symbol(sym.IsOpen, yychar, yyline, yytext());}
[I][s][C][l][o][s][e]               {return new Symbol(sym.IsClose, yychar, yyline, yytext());}
[I][s][L][o][c][k]                  {return new Symbol(sym.IsLock, yychar, yyline, yytext());}
[I][s][U][n][l][o][c][k]            {return new Symbol(sym.IsUnlock, yychar, yyline, yytext());}
[U][n][l][o][c][k][A][t]            {return new Symbol(sym.UnlockAt, yychar, yyline, yytext());}
[L][o][c][k][A][t]                  {return new Symbol(sym.LockAt, yychar, yyline, yytext());}
[C][a][m][e][r][a]                  {return new Symbol(sym.Camera, yychar, yyline, yytext());}
[R][e][c][o][r][d]                  {return new Symbol(sym.Record, yychar, yyline, yytext());}
[S][t][o][p][R][e][c]               {return new Symbol(sym.StopRec, yychar, yyline, yytext());}
[M][o][v][e]                        {return new Symbol(sym.Move, yychar, yyline, yytext());}
[I][s][M][o][v][e]                  {return new Symbol(sym.IsMove, yychar, yyline, yytext());}
[A][l][a][r][m]                     {return new Symbol(sym.Alarm, yychar, yyline, yytext());}
[D][e][v][i][c][e]                  {return new Symbol(sym.Device, yychar, yyline, yytext());}
[I][s][I][n]                        {return new Symbol(sym.IsIn, yychar, yyline, yytext());}
[I][s][O][u][t]                     {return new Symbol(sym.IsOut, yychar, yyline, yytext());}
[S][m][a][r][t][C][a][m][e][r][a]   {return new Symbol(sym.SmartCamera, yychar, yyline, yytext());}
[P][r][o][g][r][a][m]               {return new Symbol(sym.Program, yychar, yyline, yytext());}
[T][h][i][s]                        {return new Symbol(sym.This, yychar, yyline, yytext());}
[D][o][o][r][b][e][l][l]            {return new Symbol(sym.Doorbell, yychar, yyline, yytext());}
[F][a][c][e][C][h][e][c][k]         {return new Symbol(sym.FaceCheck, yychar, yyline, yytext());}
[S][a][v][e][F][a][c][e]            {return new Symbol(sym.SaveFace, yychar, yyline, yytext());}
[D][e][l][e][t][e][F][a][c][e]      {return new Symbol(sym.DeleteFace, yychar, yyline, yytext());}
[T][r][u][e]                        {return new Symbol(sym.True, yychar, yyline, yytext());}
[F][a][l][s][e]                     {return new Symbol(sym.False, yychar, yyline, yytext());}
[I][s][O][n]                        {return new Symbol(sym.IsOn, yychar, yyline, yytext());}

/*----------- | Funciones de WatchDog |-----------------------------*/
[R][e][c][o][r][d][T][i][m][e]      {return new Symbol(sym.RecordTime, yychar, yyline, yytext());}
[C][a][p][t][u][r][e]               {return new Symbol(sym.Capture, yychar, yyline, yytext());}
[W][h][i][s][t][l][e]               {return new Symbol(sym.Whistle, yychar, yyline, yytext());}
[I][s][S][o][u][n][d]               {return new Symbol(sym.IsSound, yychar, yyline, yytext());}
[A][v][a][n][z][a][r]               {return new Symbol(sym.Avanzar, yychar, yyline, yytext());}
[R][e][t][r][o][c][e][d][e][r]      {return new Symbol(sym.Retroceder, yychar, yyline, yytext());}
[D][e][t][e][n][e][r]               {return new Symbol(sym.Detener, yychar, yyline, yytext());}
[M][i][c][r][o]                     {return new Symbol(sym.Micro, yychar, yyline, yytext());}
[I][m][p][o][r][t]                  {return new Symbol(sym.Import, yychar, yyline, yytext());}



/* ------- | Tipos de datos | ------- */

[I][n][t]       {return new Symbol(sym.Int, yychar, yyline, yytext());}
[T][e][x][t]    {return new Symbol(sym.Text, yychar, yyline, yytext());}
[F][l][o][a][t] {return new Symbol(sym.Float, yychar, yyline, yytext());}
[B][o][o][l]    {return new Symbol(sym.Bool, yychar, yyline, yytext());}

/* ------- | Funciones I/O | ------- */

[R][e][c][e][i][v][e]      {return new Symbol(sym.Receive, yychar, yyline, yytext());}
[D][i][s][p][l][a][y]   {return new Symbol(sym.Display, yychar, yyline, yytext());}

/* ------- | Estructuras de control | ------- */

[F][o][r]               {return new Symbol(sym.For, yychar, yyline, yytext());}
[W][h][i][l][e]         {return new Symbol(sym.While, yychar, yyline, yytext());}
[I][f]                  {return new Symbol(sym.If, yychar, yyline, yytext());}
[E][l][s][e]            {return new Symbol(sym.Else, yychar, yyline, yytext());}
[W][h][e][n]            {return new Symbol(sym.When, yychar, yyline, yytext());}
[D][e][f][a][u][l][t]   {return new Symbol(sym.Default, yychar, yyline, yytext());}
[T][h][e][n]            {return new Symbol(sym.Then, yychar, yyline, yytext());}

/* ----------------> Operadores <-----------------------*/

/* ------- | Op. Relacionales | ------- */

( "=>" ) {return new Symbol(sym.AsignacionArrow, yychar, yyline, yytext());}
( "=" ) {return new Symbol(sym.Asignacion, yychar, yyline, yytext());}
( "==" ) {return new Symbol(sym.Igual, yychar, yyline, yytext());}
( "<" ) {return new Symbol(sym.Menor, yychar, yyline, yytext());}
( ">" ) {return new Symbol(sym.Mayor, yychar, yyline, yytext());}
( "<=" ) {return new Symbol(sym.Mayor_igual, yychar, yyline, yytext());}
( ">=" ) {return new Symbol(sym.Menor_igual, yychar, yyline, yytext());}
( "!=" ) {return new Symbol(sym.Diferente, yychar, yyline, yytext());}

/* ------- | Op. Aritmeticos | ------- */

( "+" ) {return new Symbol(sym.Suma, yychar, yyline, yytext());}
( "-" ) {return new Symbol(sym.Resta, yychar, yyline, yytext());}
( "*" ) {return new Symbol(sym.Multiplicacion, yychar, yyline, yytext());}
( "/" ) {return new Symbol(sym.Division, yychar, yyline, yytext());}

/* ------- | Op. Lógicos | ------- */

( "&&" ) {return new Symbol(sym.And, yychar, yyline, yytext());}
( "||" ) {return new Symbol(sym.Or, yychar, yyline, yytext());}

/* ------- | Op. Incrementales | ------- */

( "--" ) {return new Symbol(sym.Dec, yychar, yyline, yytext());}
( "++" ) {return new Symbol(sym.Inc, yychar, yyline, yytext());}

/* ------- | Op. Agrupación | ------- */

( "(" ) {return new Symbol(sym.Parentesis_izq, yychar, yyline, yytext());}
( ")" ) {return new Symbol(sym.Parentesis_der, yychar, yyline, yytext());}
( "[" ) {return new Symbol(sym.Corchete_izq, yychar, yyline, yytext());}
( "]" ) {return new Symbol(sym.Corchete_der, yychar, yyline, yytext());}
( "{" ) {return new Symbol(sym.Llave_izq, yychar, yyline, yytext());}
( "}" ) {return new Symbol(sym.Llave_der, yychar, yyline, yytext());}

/* Identificador */

{L}({L}|{D})* { return new Symbol(sym.Identificador, yychar, yyline, yytext()); }
{D}({L}|{D})*{L}({L}|{D})* { return new Symbol(sym.ERROR3, yychar, yyline, yytext()); }

/* Numero */

{D}+                            {return new Symbol(sym.Numero_Entero, yychar, yyline, yytext());}
{D}+"."{D}*                     {return new Symbol(sym.Numero_Flotante, yychar, yyline, yytext());}
{D}+[eE][+-]?{D}*               {return new Symbol(sym.Numero_Euler, yychar, yyline, yytext());}
{D}+"."{D}*[eE][+-]?{D}*        {return new Symbol(sym.Numero_Euler_Flotante, yychar, yyline, yytext());} 

/* Error de analisis */

([a-zA-ZÑñ_0-9\!\,\.\=\+\-\*\/\;\:\[\]\(\)\{\}\<\>\']* [^a-zA-ZÑñ_0-9\!\,\.\=\+\-\*\/\;\:\[\]\(\)\{\}\<\>\'\t\r\n ]+ [a-zA-ZÑñ_0-9\!\,\.\=\+\-\*\/\;\:\[\]\(\)\{\}\<\>\']*)+ {return new Symbol(sym.ERROR, yychar, yyline, yytext());}
. {return new Symbol(sym.ERROR2, yychar, yyline, yytext());}