#include <iostream>
#include <windows.h>
#include <stdio.h>
#define CONSOLE_H
#include <conio.h>
#include <time.h>
#include <stdlib.h>

/*
Lista de errores

-El salir en el main al volver de resultado al finalizar el nivel no funciona
siempre manda al nivel de nuevo(el salir la primera vez en el main funciona bien)
se quedara la tecla guardada o algo?.

To do list.
-Añadir ranking?

Notas
-Virtual keys

*/
using namespace std;
struct base{
    unsigned char tipo; //Unsigned char OBLIGATORIO PORQUE SON NUMEROS ASCII, si no da error al comparar en chocar
    int lugarX;//Horizontal
    int lugarY;//Vertical
    struct base *sig;
};
struct player{
    char tipo=1;
    int lugarX=24;
    int lugarY=7;
    struct base *sig;
};

void opciones(int&,int&,float&);
void printOpciones(int,int,float,int);
void nivel1(); //Control de el nivel
void interfaz(int,int,int,int,int,int,int,int); //"mapa", marcos multiusos... X e Y de donde a donde ira el marco
void movimiento(struct player&); //Movimiento y colision del jugador
void resultado (bool,int); // Estadisticas al final
void distRecorrida(int,int,int,int); //distancia reocrrida del jugador Posicion X,posicion Y
void vida(int);
void gotoxy(int,int);
void color(int); //colores
bool teclaPresionada(int);

int main()//menu
{
    int resp;
    char tecla=0;
    bool primero;
    Sleep(140);
    fflush(stdin);
    //menu

        /*Importante
            Si se ha tecleado, guardame la tecla.Es otra forma
                if(kbhit()){
                    tecla=getch();
                }
            Si la tecla es X, haz Y.
                if(tecla==13){
                }
            Se recomienda no usar tanto kbhit debido a que usa el buffer de entrada.
            Debido a mal funcionamiento para algunas cosas como el movimiento
            mejor usar GetKeyState y leer si la tecla esta pulsada.
            ej:
        */
        color(1);
        primero=true;
        interfaz(26,54,1,10,26,53,2,10);
    do{
        if(primero){
            gotoxy(35,2);
            printf ("RUNI RUNI");
            gotoxy(36,4);
            printf ("Iniciar");
            gotoxy(37,6);
            printf ("Salir");
            gotoxy(28,9);

            gotoxy(34,4);
            cout<<char(16);
            gotoxy(34,6);
            cout<<char(255);
            gotoxy(35,4);
            resp=1;

            primero=false;
        }

        if(kbhit()){ // **
            tecla=getch();
        }

        if(teclaPresionada(0x57)){  //w
            gotoxy(34,4);
            cout<<char(16);
            gotoxy(34,6);
            cout<<char(255);
            gotoxy(35,4);
            resp=1;
        }
        if(teclaPresionada(0x53)){  //s
            gotoxy(34,6);
            cout<<char(16);
            gotoxy(34,4);
            cout<<char(255);
            gotoxy(35,6);
            resp=2;
        }
        if(teclaPresionada(0x0D)){// deja rastros si no se pone **
        //if(tecla==13){
            if(resp==1){
                //fflush(stdin);
                nivel1();
                primero=true;
            }
            else{
                system("cls");
                break;
            }
        }
    }while(0!=1);
    return 0;
}

void nivel1()
{
    //limpia pantalla
    //fflush(stdin);
    Sleep(400);
    system("cls");
    struct base *pri=NULL, *nuevo,*aux,*borrar;//Suelo
    struct player player1;
    srand(time(NULL));

    int largoTotal;
    int espacios=3;
    int largo=300;
    float velocidad=2.5;
    int vale; //control de scanf
    bool chocado=false;
    bool perdio=false;//Para el aleatorio.
    int puntuaje=0;
    int choques=3;
    int opEspacios=3;

    opciones(largo,opEspacios,velocidad);
    largoTotal=largo;
    interfaz(19,66,4,8,19,65,5,8);//Interfaz
    vida(3);

    for(largo;largo!=0;largo--)//for de cantidad de obstaculos
    {
        //Creación de un nuevo obstaculo
        nuevo=(struct base *)malloc(sizeof(struct base));
        vale=rand()%100;
          //Los espacios son los huecos entre obstaculos, variar
          //para cambiar difultad si se quiere y el valor de los if
        if(espacios!=0)
        {
            nuevo->tipo=255;
            espacios--;
        }else{
            if(vale>=80)
            {
                nuevo->tipo=186;
                espacios=opEspacios;
            }else
            {
               nuevo->tipo=255;
            }
        }
        nuevo->lugarX=65;
        nuevo->lugarY=7;
        nuevo->sig=pri;
        pri=nuevo;

        //Movemos los distintos cachos de obstaculos generados de izquierda a derecha
        for(aux=pri;aux!=NULL;aux=aux->sig)
        {

            aux->lugarX=aux->lugarX-1;// se mueve 1 para atras el obstáculo
           //movimiento jugador
            movimiento(player1);
            gotoxy(player1.lugarX,player1.lugarY);
            printf("%c",player1.tipo);
            // fin movimiento jugador
           /*
        //comprobamos si se ha chocado(parte para enseñar o comprobar)
            gotoxy(5,5);
            cout<<aux->tipo;
            gotoxy(5,11);
            cout<<"player1.lugarY"<<player1.lugarY;
            gotoxy(5,12);
            cout<<"aux->lugarY"<<aux->lugarY;
            gotoxy(5,14);
            cout<<"player1.lugarX"<<player1.lugarX;
            gotoxy(5,15);
            cout<<"aux->lugarX"<<aux->lugarX;
            */

           // comprobamos si se ha chocado
           if(aux->lugarY==player1.lugarY)
           {
               if(aux->lugarX==player1.lugarX)
               {
                   //TIENE QUE SER UNSIGNED CHAR PORQUE UTILIZO 0-255(ASCII)
                   if(aux->tipo<255){
                        /* //enseñar o comprobar
                        gotoxy(5,17);
                        cout<<"choques"<<choques;
                   */
                        choques--;
                        chocado=true;
                   }else if(aux->tipo==255)
                   {
                        puntuaje++;
                   }
               }
           }
              //fin comprobamos si se ha chocado
            //puntos
            gotoxy(25,9);
            cout<<"Puntos: "<<puntuaje;
            distRecorrida(20,3,largo,largoTotal);//distancia recorrida
            if(choques==0)
            {
                break;
            }
            //vidas
            if(chocado)
            {
                vida(choques);
                chocado=false;
            }
            gotoxy(aux->lugarX,aux->lugarY);

            if(aux->lugarX==20)
            {
                /*Si esta en la posicion 19 esta fuera de pantalla y
                por ende lo borramos de nuestra lista*/
                borrar=aux->sig;
                free(borrar);

                /*Dado que esta en posición X=20 no queremos sacar mas, por tanto
                cambiaremos a null para que no pare el for*/
                aux->sig=NULL;
            }

            printf("%c",aux->tipo);
            Sleep(velocidad);//relentizamos un poco el programa
        }
        if(choques==0)
        {
            perdio=true;
            break;
        }
    }
    system("cls");
    resultado(perdio,puntuaje);
    //main();
}


void opciones(int &largo,int &espacios,float& velocidad)
{
    float a=2.5;
    int punteroY=10;
    bool pri=true,modificado=false;
    int shift=50;
    char tecla;

    system("cls");
    Sleep(140);


    do{
        if(pri){
            printOpciones(largo,espacios,velocidad,10);
            pri=false;
        }
        if(kbhit()){
            tecla=getch();
        }
        shift=90;
        if(teclaPresionada(0xA0)){// shift izquierdo
            shift=20;
        }
        if(teclaPresionada(0xA2)){//control izquierdo
            shift=4;
        }
        if(teclaPresionada(0x57)){  //w
            if(punteroY>4){
                gotoxy(13,punteroY);
                printf("%c",255);
                punteroY=punteroY-2;
                gotoxy(30,1);
                gotoxy(13,punteroY);
                printf("%c",16);
                Sleep(250);
            }
        }
        if(teclaPresionada(0x53)){  //s
            if(punteroY<10){
                gotoxy(13,punteroY);
                printf("%c",255);
                punteroY=punteroY+2;
                gotoxy(30,1);
                gotoxy(13,punteroY);
                printf("%c",16);
                Sleep(250);
            }
        }
        if(teclaPresionada(0x41)){  //a
            if(punteroY==4){
                largo--;
            }else if(punteroY==6){
                if(espacios>0){
                    espacios--;
                }
                Sleep(150);
            }else if(punteroY==8){
                a=a-0.01;
                velocidad=a;
            }
            modificado=true;
            Sleep(shift);
        }
        if(teclaPresionada(0x44)){  //d
            if(punteroY==4){
                largo++;
            }else if(punteroY==6){
                espacios++;
                Sleep(150);
            }else if(punteroY==8){
                a=a+0.01;
                velocidad=a;
            }
            modificado=true;
            Sleep(shift);
        }
        if(punteroY==10){
            if(teclaPresionada(0x0D)){
                if(punteroY=10){
                break;
                }
            }
        }
        if(modificado){
            for(int i=50;i!=56;i++)
            {
                gotoxy(i,4);
                printf("%c",255);
                gotoxy(i,6);
                printf("%c",255);
                gotoxy(i,8);
            }
            gotoxy(50,4);
            printf("%d",largo);
            gotoxy(50,6);
            printf("%d",espacios);
            gotoxy(50,8);
            printf("%4.2f",velocidad);
            modificado=false;
        }

    }while(0!=1);
    //nivel1();
}

void printOpciones(int largo,int espacios,float velocidad, int punteroY)
{
        interfaz(12,74,1,20,12,73,2,20);
        for(int i=13;i!=73;i++)
        {
            gotoxy(i,12);
            cout<<char(205);
        }
        for(int i=2;i!=12;i++)
        {
            gotoxy(57,i);
            cout<<char(186);
        }

        gotoxy(15,4);
        printf("Largura(300-medio)");
        gotoxy(15,6);
        printf("Espacios entre obst%cculos",160);
        gotoxy(15,8);
        printf("Velocidad(-,mas r%cpido)",160);
        gotoxy(15,10);
        printf("Continuar");
        gotoxy(50,4);
        printf("%d",largo);
        gotoxy(50,6);
        printf("%d",espacios);
        gotoxy(50,8);
        printf("%4.2f",velocidad);
        gotoxy(25,2);
        printf("Configuraci%cn partida",162);
        gotoxy(42,10);
        printf("Shift+  Ctrl++");

        gotoxy(15,14);
        printf("Objetivos:");
        gotoxy(15,16);
        printf("-Consigue puntos tocando el suelo el mayor tiempo posible");
        gotoxy(15,18);
        printf("-Evita los obst%cculos, cada golpe te restar%c una vida",160,160);

        gotoxy(61,2);
        printf("Controles");
        gotoxy(63,8);
        printf("Pausa");
        gotoxy(65,10);
        printf("P");
        gotoxy(58,4);
        printf("Arriba | Abajo");
        gotoxy(61,6);
        printf("W");
        gotoxy(69,6);
        printf("S");

        gotoxy(13,punteroY);
        printf("%c",16);
}
//Primeros 4 int lugar horizontal y vertical de las lineas superiores e inferiores
//ej:
//Primeros 2 int lugar de comienzo y final de la linea horizontalmente(superior e inferior)
//Siguientes 2 int lugar de comienzo y final de la linea Verticualmente(superior e inferior)
void interfaz(int xlargoInicio,int xlargoFin,int xaltoInicio, int xaltoFin,int ylargoInicio,int ylargoFin,int yaltoInicio, int yaltoFin)
{
    system("cls");

    //Parte superior y inferior
        for(xlargoInicio;xlargoInicio!=xlargoFin;xlargoInicio++)
    {
        gotoxy(xlargoInicio,xaltoInicio);
        cout<<char(205);
        gotoxy(xlargoInicio,xaltoFin);
        cout<<char(205);
    }
    //lados
        for(yaltoInicio;yaltoInicio!=yaltoFin;yaltoInicio++)
        {
            gotoxy(ylargoInicio,yaltoInicio);
            cout<<char(186);
            gotoxy(ylargoFin,yaltoInicio);
            cout<<char(186);
        }

}

void movimiento(struct player &player1)
{

    //kbhit es una opcion para otras cosas, para un juego/este caso no acaba de funcionar bien
    //mejor usar GetKeyState para ver si esta pulsando la tecla y si esta pulsando
    //efectua la accion.
    //borrar
    gotoxy(player1.lugarX,player1.lugarY);
    cout<<" ";
    if(teclaPresionada(0x57)){  //w
        player1.lugarY=6;
    }
    if(teclaPresionada(0x53)){  //s
        player1.lugarY=7;
    }
    if(teclaPresionada(0x50)){//p
        gotoxy(20,2);
        system("pause");
        gotoxy(20,2);
        printf("                                       ");
    }
}

void resultado (bool perdio,int puntuaje)//Recibe puntos y un bool si se ha chocado o no
{
    char resp;
    char tecla;
    bool primer=true;
    int conti=5;
    //perder o ganar
    interfaz(26,50,1,10,26,49,2,10);
    gotoxy(34,2);
    if(perdio)
    {
        printf("You lose");
    }else
    {
        printf("You win");
    }

    do{
        if(primer){
            gotoxy(33,4);
            cout<<"Points: "<<puntuaje;
            gotoxy(30,8);
            cout<<" Volver al menu";
            gotoxy(30,9);
            cout<<" Jugar de nuevo";

            gotoxy(29,8);
            cout<<char(16);
            gotoxy(29,9);
            cout<<char(255);
            gotoxy(30,8);
            resp=1;
            primer=false;
        }
        if(kbhit()){
            tecla=getch();
        }

        if(teclaPresionada(0x57)){  //w
            gotoxy(29,8);
            cout<<char(16);
            gotoxy(29,9);
            cout<<char(255);
            gotoxy(30,8);
            resp=1;
        }
        if(teclaPresionada(0x53)){  //s
            gotoxy(29,9);
            cout<<char(16);
            gotoxy(29,8);
            cout<<char(255);
            gotoxy(30,9);
            resp=2;
        }
        if(teclaPresionada(0x0D)){//
        //if(tecla==13){
            if(resp==1){
                //fflush(stdin);
                system("cls");
                main();
                break;
            }
            else{
                nivel1();
            }
        }
    }while(0!=1);
}

//distancia recorrida, separado para sumar y restar solo llamando
void distRecorrida(int x,int y,int largo,int largoTotal){
    int recorrido;
    gotoxy(x,y);
    recorrido=largoTotal-largo;
    cout<<"Recorrido: "<<recorrido;
}

void vida(int numVidas){

    int x=19;
    //rellenamos blancos y luego exponemos las vidas correspondientes
    gotoxy(22,9);
    cout<<char(255);
    gotoxy(21,9);
    cout<<char(255);
    gotoxy(20,9);
    cout<<char(255);
    color(2);
    for(numVidas;numVidas!=0;numVidas--)
    {
        x++;
        gotoxy(x,9);
        cout<<char(3);
    }
    color(1);
}

//mandar a un punto el cursor
void gotoxy(int x, int y){
  COORD point;
  point.X = x; point.Y = y;
  SetConsoleCursorPosition(GetStdHandle(STD_OUTPUT_HANDLE), point);
}

//colores, libreria windws.h
void color(int num)
{
    switch (num){

        case 1://cyan
        SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), FOREGROUND_INTENSITY | FOREGROUND_GREEN |FOREGROUND_BLUE);
        break;
        case 2://red
        SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE),FOREGROUND_INTENSITY | FOREGROUND_RED);
        break;
    }
}
//control de tecla (KeyDown) utiliza las virtualKey, libreria windows
bool teclaPresionada(int tecla) {
    return GetKeyState(tecla) < 0;
}






