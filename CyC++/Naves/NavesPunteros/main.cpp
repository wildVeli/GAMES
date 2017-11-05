#include <iostream>
#include <windows.h>
#include<cstdio>

using namespace std;

struct naves{
    unsigned char tipo;
    int posicionX;
    int posicionY;
};

struct balas {
    unsigned char tipo=250;
    int posicionX;
    int posicionY;
    struct balas *sig;
    struct balas *anterior;//modi
};

void ascii();
void nivel();
void movimiento(struct naves&);
//void disparo(struct balas**,int,int);
void disparo2(struct balas *&,int,int);
//void moverBalas(struct balas *);
void moverBalas2(struct balas *,struct balas *);
void interfaz();
void gotoxy(int,int);
bool teclaPresionada(int);

int main()
{
    int resp;
    cout<<"1-comenzar"<<endl;
    cin>>resp;
    if(resp==1){
        nivel();
    }
    return 0;
}
void nivel()
{
    struct naves jugador;
    struct balas *pri=NULL;
    struct balas *fin=NULL;
    struct balas *nuevo;

    jugador.tipo=127;
    jugador.posicionX=42;
    jugador.posicionY=22;

    //creamos el primero de la lista, nunca se borrara
    nuevo=(struct balas*)malloc(sizeof(struct balas));
    nuevo->posicionX=jugador.posicionX;
    nuevo->posicionY=jugador.posicionY-1;
    nuevo->anterior=pri;
    nuevo->sig=NULL;
    pri=nuevo;
    fin=nuevo;

    gotoxy(jugador.posicionX,jugador.posicionY);
    printf("%c",jugador.tipo);
    gotoxy(jugador.posicionX,jugador.posicionY);
    interfaz();

    do
    {
        //movimiento


        //jugador dispara
        //disparo(&pri,jugador.posicionX,jugador.posicionY);
        disparo2(fin,jugador.posicionX,jugador.posicionY);
        //moverBalas(pri);
        moverBalas2(fin,pri);

        movimiento(jugador);
        Sleep(30);

    }while(1!=0);
}

void movimiento(struct naves& jugador)
{
    int x=jugador.posicionX;
    int y=jugador.posicionY;
    bool movido=false;
/*
    if(teclaPresionada(0x57)) //w
    {
        if(jugador.posicionY>5)
        {
            jugador.posicionY--;
            movido=true;
        }
    }
    if(teclaPresionada(0x53)) //s
    {
        if(jugador.posicionY<24)
        {
          jugador.posicionY++;
            movido=true;

        }


    }
    */
    if(teclaPresionada(0x41)) //a
    {
        if(jugador.posicionX>32)
        {
            jugador.posicionX--;
            movido=true;
        }
    }
    if(teclaPresionada(0x44)) //d
    {
        if(jugador.posicionX<49)
        {
            jugador.posicionX++;
            movido=true;
        }
    }

   //if(movido)
    //{
        gotoxy(x,y);
        printf("%c",255);
        gotoxy(jugador.posicionX,jugador.posicionY);
        printf("%c",jugador.tipo);
        gotoxy(jugador.posicionX,jugador.posicionY);
        movido=false;
    //}

}
/*
void disparo(struct balas **pri,int jugadorX,int jugadorY)
{
    struct balas *nuevo;

    if(teclaPresionada(0x20))
    {
        nuevo=(struct balas*)malloc(sizeof(struct balas));
        nuevo->posicionX=jugadorX;
        nuevo->posicionY=jugadorY-1;
        nuevo->sig=*pri;
        *pri=nuevo;
    }

    //for()
}

void moverBalas(struct balas *pri)
{
    struct balas *aux,*borrar;
    aux=pri;
    bool guardar=false;
    int a=0;

    //con este metodo la lista siempre tendra al menos 1 elemento en 3. Se puede optimizar
    for(aux;aux!=NULL;aux=aux->sig)
    {
        //limpia la pantalla
        gotoxy(aux->posicionX,aux->posicionY);
        printf("%c",255);

        //si esta en >3 bajamos uno y lo imprimimos
        if(aux->posicionY>3)
        {
            aux->posicionY--;
            gotoxy(aux->posicionX,aux->posicionY);
            aux->tipo=250;
            printf("%c",aux->tipo);
        }else//si no esta en >3, o lo que es lo mismo, que es 3.if(aux->posicionY==3)
        {
            gotoxy(aux->posicionX,aux->posicionY);
            aux->tipo=255;
            printf("%c",aux->tipo);
            //Seleccionamos el siguiente de la lista y lo borramos
            borrar=aux->sig;
            free(borrar);
            //ponemos a null el nuevo final de la lista.(el anterior al borrado)
            aux->sig=NULL;
        }


        a++;
        gotoxy(2,2);
        printf("%c",255);
        gotoxy(3,2);
        printf("%c",255);
        gotoxy(2,2);
        cout<<"lista "<<a;
    }
    a=0;

}
*/
void disparo2(struct balas *&fin,int jugadorX,int jugadorY)
{
    struct balas *nuevo;
    if(teclaPresionada(0x20))
    {
        nuevo=(struct balas*)malloc(sizeof(struct balas));
        nuevo->posicionX=jugadorX;
        nuevo->posicionY=jugadorY-1;
        nuevo->anterior=fin;
        nuevo->sig=NULL;
        fin->sig=nuevo;
        fin=nuevo;
    }
}
void moverBalas2(struct balas *fin,struct balas *pri)
{
    struct balas *aux=fin;
    aux=fin;
    //recorremos haasta el primero y movemos
    for(fin;fin!=pri;fin=fin->anterior)
    {
        if(fin->posicionY>3)
        {
            gotoxy(fin->posicionX,fin->posicionY);
            printf("%c",255);
            fin->posicionY--;
            gotoxy(fin->posicionX,fin->posicionY);
            printf("%c",250);
        }
    }
    //recorremos otra vez para saber cual esta en 3 y ir borrando
    fin=aux;

    //(A-X-B , quitamos X y enlazamos A siguiente y B anterior)
    for(fin;fin!=pri;fin=fin->anterior)
    {
        if(fin->posicionY==3)
        {
            gotoxy(fin->posicionX,fin->posicionY);
            printf("%c",255);
            //modificamos el anterior de la lista
            aux=fin->anterior;
            aux->sig=fin->sig;
            //modificamos el siguiente de la lista
           if(aux->sig!=NULL)
            {
                aux=fin->sig;
                aux->anterior=fin->anterior;

            }
            //ESTO PETA, REVISAR
/*
                aux=fin;
                //fin=aux->anterior;
                //free(aux);
*/

        }
    }

}
void interfaz()
{
    for(int i=31;i!=60;i++)
    {
        gotoxy(i,2);
        printf("%c",196);
        gotoxy(i,25);
        printf("%c",196);

    }
    gotoxy(31,2);
    printf("%c",218);
    gotoxy(50,2);
    printf("%c",194);
    gotoxy(31,25);
    printf("%c",192);
    gotoxy(50,25);
    printf("%c",193);

    for(int i=3;i!=25;i++)
    {
        gotoxy(31,i);
        printf("%c",179);
        gotoxy(50,i);
        printf("%c",179);
        gotoxy(60,i);
        printf("%c",179);
    }
    gotoxy(60,2);
    printf("%c",191);
    gotoxy(60,25);
    printf("%c",217);
}

void ascii()
{

    cout <<char(127)<< endl;
    cout <<char(250)<< endl;

    for(int i=1;i<256;i++){
        Sleep(100);
        cout <<char(i)<< endl;
    }
    cout <<char(249)<< endl;
    cout <<char(248)<< endl;
    cout <<char(254)<< endl;
    cout <<char(46)<< endl;
}

void gotoxy(int x, int y){
  COORD point;
  point.X = x; point.Y = y;
  SetConsoleCursorPosition(GetStdHandle(STD_OUTPUT_HANDLE), point);
}

bool teclaPresionada(int tecla) {
    return GetKeyState(tecla) < 0;
}
