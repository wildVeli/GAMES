#include <iostream>
#include <windows.h>
#include <cstdio>
#include <vector>
#include <time.h>
#include <cstdlib>

using namespace std;

class Naves{
private:
    unsigned char tipo;
    unsigned short int posicionX;
    unsigned short int posicionY;
public:
    unsigned char getTipo()
    {
        return tipo;
    }
    unsigned short int getPosicionX()
    {
        return posicionX;
    }
    unsigned short int getPosicionY()
    {
        return posicionY;
    }
    void setTipo (unsigned char x)
    {
        tipo=x;
    }
    void setPosicionX (int x)
    {
        posicionX=x;
    }
    void setPosicionY (int x)
    {
        posicionY=x;
    }
};
class Balas{
private:
    unsigned char tipo=250;
    unsigned short int posicionX;
    unsigned short int posicionY;
public:
    unsigned char getTipo()
    {
        return tipo;
    }
    unsigned short int getPosicionX()
    {
        return posicionX;
    }
    unsigned short int getPosicionY()
    {
        return posicionY;
    }
    void setTipo (unsigned char x)
    {
        tipo=x;
    }
    void setPosicionX (int x)
    {
        posicionX=x;
    }
    void setPosicionY (int x)
    {
        posicionY=x;
    }
};
/* NOTAS
-UTILIZANDO UNSIGNED SHORT INT PARA MAYOR "FLUIDEZ", EN CASO DE PROBLEMAS REVISAR
*/

/*Ideas o Mejoras
--¿Disparos de enemigos?.
-Choques de balas con jugador.
-Movimiento aleatorio de enemigos
/*To do list

-Comprobar choques, alguna rara vez en vez de chocar traspasa bala,
parcheado contando si la bala choca en Y+1(1 mas abajo)(SOLUCIONADO?CREO)

-Ajustar velocidad general de parámetros.

*/
/*
Colores
0-Negro  9-F(IGUAL PERO CLARITO)
1-azul
2-verde
3-Aqua
4-Rojo
5-Morado
6-Amarillo
7-blanco
8-gris

*/
void ascii();
//mueve el jugador
void movimientoJugador(vector<Naves>&);//funciona
//Crea balas del jugador
void crearbalasJug(vector <Balas> &, vector <Naves>,bool&);//funciona
//mueve balas del jugador
void moverbalasJug(vector <Balas> &, vector <Naves>);//funciona
//Crea enemigos
void crearEnemigos(vector<Naves>&);//funciona, crear el siguiente separado
//Mueve enemigos
void moverEnemigos(vector<Naves>&,unsigned short int &);//funciona
//Comprueba Choque de las balas de jugador con enemigos
//Comprueba Choque del jugador con enemigos
bool coliBalaJugYJug(vector<Balas> &,vector<Naves>&,int &puntos);
void puntuacion(int,int&);
void vidas(unsigned short int&);
void interfaz();//funciona
void gotoxy(int,int);//funciona
void color(int);
bool teclaPresionada(int);//funciona
void inicio();

int main()
{
    inicio();
    system("cls");
    class Naves navecitas;
    class Balas balitas;
    vector <Naves> naves; //la 0 es el jugador NO BORRAR
    //vector <Naves> navesEnemigos;
    vector <Balas> balasJugador;
    vector <Balas> balasNPC;

    //Marcadores
    int puntos=0;
    int puntAnte;
    //Variables tiempo de creacion y movimiento
    unsigned short int vida=3;
    unsigned short int timePlayerShoot=0;
    unsigned short int lapsoEnemigos=0;
    unsigned short int moverEnemi=0;
    unsigned short int moverJugador=0;
    //Fin variables opciones;
    bool sinVidas=false;
    bool pri=true;
    color(1);
    //ponemos al jugador en el 1 del vector para trabajar con el
    if(pri)
    {
        navecitas.setTipo(127);
        navecitas.setPosicionX(41);
        navecitas.setPosicionY(20);
        naves.push_back(navecitas);
        pri=false;
    }
    gotoxy(naves[0].getPosicionX(),naves[0].getPosicionY());
    printf("%c",naves[0].getTipo());

//SUSTITUIR
    /*  HANDLE hstdin  = GetStdHandle( STD_INPUT_HANDLE  );
	HANDLE hstdout = GetStdHandle( STD_OUTPUT_HANDLE );
	SetConsoleTextAttribute( hstdout, /*0x5E*///0xD );*/
//
    interfaz();
    srand(time(NULL));
    bool disparado=false;
    bool jugadorChoco=false;
    do{
        if(moverJugador>2)
        {
            movimientoJugador(naves);
            moverJugador=0;
        }

        //Cada cierto tiempo puede disaprar 4 o mas
        if(timePlayerShoot>4){
            crearbalasJug(balasJugador,naves,disparado);
            //si dispara tendra que espererar otra ves ese tiempo, el cont bajara a 0
            if(disparado){
                timePlayerShoot=0;
                disparado=true;
            }
        }
        moverbalasJug(balasJugador,naves);
        //Guardo los puntos anteriores
        puntAnte=puntos;
        //Se comprueba despues de mover la bala si ha chocado con el enemigo
        jugadorChoco=coliBalaJugYJug(balasJugador,naves,puntos);

        puntuacion(puntAnte,puntos);
        if(lapsoEnemigos==20)
        {
            crearEnemigos(naves);
            lapsoEnemigos=0;
        }

        if(moverEnemi==7)
        {
            unsigned short int h=vida;
            moverEnemigos(naves,vida);
            if(vida<h)
            {
                vidas(vida);
                if(vida==0)
                {
                    sinVidas=true;
                }
            }
            moverEnemi=0;
        }
        //Se comprueba despues de mover el enemigo si ha chocado con la bala
        jugadorChoco=coliBalaJugYJug(balasJugador,naves,puntos);
        if(jugadorChoco||sinVidas)
        {
            break;
        }
        moverJugador++;
        moverEnemi++;
        lapsoEnemigos++;
        timePlayerShoot++;
        Sleep(20);
        if(teclaPresionada(0x50))//p
        {
            gotoxy(1,1);
            system("pause");
            gotoxy(1,1);
            printf("                                            ");
        }
    }while(0!=1);
    gotoxy(38,9);
    printf("Perdiste");
    gotoxy(38,11);
    printf("Puntos: %d",puntos);
    gotoxy(33,13);
    printf("J Salir del juego");

    do{
        if(teclaPresionada(0x4A)) //J
        {
            break;
        }
    }while(1!=0);
    return 0;
}
void inicio()
{
    unsigned short int x;
    system("cls");
    color(0);
    gotoxy(33,2);
    printf("PIU PIU");
    gotoxy(28,3);
    printf("You shall not pass!");
    color(1);
    gotoxy(20,5);
    printf("\t\tControles");
    gotoxy(26,7);
    printf("   P--------Pausar");
    gotoxy(26,8);
    printf("   W--------Moverse hacia arriba");
    gotoxy(26,9);
    printf("   S--------Moverse hacia abajo");
    gotoxy(26,10);
    printf("   D--------Moverse hacia la derecha");
    gotoxy(26,11);
    printf("   A--------Moverse hacia hacia la izquierda");
    gotoxy(26,12);
    printf("ESPACIO-----Disparo   ESPACIO");
    gotoxy(13,14);
    printf("\t\tPara jugar pulsa ESPACIO");
    do{
        if(teclaPresionada(0x20)) //w
        {
            break;
        }
    }while(1!=0);

}

void movimientoJugador(vector <Naves> &naves)
{
    unsigned short int x=naves[0].getPosicionX();
    unsigned short int y=naves[0].getPosicionY();

    //movemos el jugador
    if(teclaPresionada(0x57)) //w
    {
        if(y>5)
        {
            y--;
        }
    }
    if(teclaPresionada(0x53)) //s
    {
        if(y<23)
        {
            y++;
        }
    }
    if(teclaPresionada(0x41)) //a
    {
        if(x>32)
        {
            x--;
        }
    }
    if(teclaPresionada(0x44)) //d
    {
        if(x<49)
        {
            x++;
        }
    }
        //mostramos por pantalla
        gotoxy(naves[0].getPosicionX(),naves[0].getPosicionY());
        printf("%c",255);
        gotoxy(x,y);
        printf("%c",naves[0].getTipo());
        gotoxy(x,y);
        //cambiamos la posicion del jugador
        naves[0].setPosicionX(x);
        naves[0].setPosicionY(y);
}

void crearbalasJug(vector<Balas> &balasJugador,vector <Naves> naves,bool& disparo)
{
    class Balas balitas;
    bool hueco=false;
    //NECESITO UNA NUEVA BALA
    if(teclaPresionada(0x20)) //espacio
    {
        //Compruebo el vector
        for(unsigned short int i=0;i<balasJugador.size();i++)
        {
            //Si una bala ha llegado a su recorrido final(arriba)
            if(balasJugador[i].getPosicionY()==3)
            {
                gotoxy(balasJugador[i].getPosicionX(),balasJugador[i].getPosicionY());
                printf("%c",255);
                //reutilizo su hueco para una nueva bala
                balasJugador[i].setPosicionX(naves[0].getPosicionX());
                balasJugador[i].setPosicionY(naves[0].getPosicionY()-1);
                balasJugador[i].setTipo(250);
                hueco=true;
                break;
            }
        }
        //Si no ha llegado ninguna, necesito un nuevo espacio en el vector
        if(!hueco)
        {
            balitas.setPosicionX(naves[0].getPosicionX());
            balitas.setPosicionY(naves[0].getPosicionY()-1);
            balitas.setTipo(250);
            balasJugador.push_back(balitas);
        }
        disparo=true;
    }

}
void moverbalasJug(vector<Balas> &balasJugador,vector <Naves> naves)
{
    //Recorro el vector de balas
    for(unsigned short int i=0;i<balasJugador.size();i++)
    {
        //Imprimo blanco donde estaba la bala
        gotoxy(balasJugador[i].getPosicionX(),balasJugador[i].getPosicionY());
        printf("%c",255);

        //si ha llegado al tope de arriba, no la imprimo
        if(balasJugador[i].getPosicionY()>3)
        {
        //Imprimo la bala en la posicion actual
            balasJugador[i].setPosicionY(balasJugador[i].getPosicionY()-1);
            gotoxy(balasJugador[i].getPosicionX(),balasJugador[i].getPosicionY());
            printf("%c",250);

        }
    }
}
void crearEnemigos(vector<Naves>& naves)
{//finalizar

    class Naves enemigos;
    unsigned short int x;
    bool hueco=false;

    //Creo un nuevo enemigo
        for(unsigned short int i=1;i<naves.size();i++)
        {
            //Si una nave ha llegado a su recorrido final(abajo)o ha sido destruida(arriba 0)
            if(naves[i].getPosicionY()==24||naves[i].getPosicionY()==1)
            {
                gotoxy(naves[i].getPosicionX(),naves[i].getPosicionY());
                printf("%c",255);
                //reutilizo su hueco para una nueva nave
                x=rand()%16;
                x=x+32;
                naves[i].setPosicionX(x);
                x=3+rand()%5;
                naves[i].setPosicionY(x);
                //puede servir  si queremos varios tipos de nave
                //naves[i].setTipo(234);
                hueco=true;
                break;
            }
        }
        if(!hueco)
        {
            x=rand()%16;
            x=x+32;
            enemigos.setPosicionX(x);
            x=3+rand()%5;
            enemigos.setPosicionY(x);
            enemigos.setTipo(234);
            naves.push_back(enemigos);
        }

}
//Si la nave se destruye va a 1, Si la nave llega a su destino va al 24
void moverEnemigos(vector<Naves>&naves,unsigned short int &vidas)
{

    for(unsigned short int i=1;i<naves.size();i++)
    {
        //borrar donde estaba
        gotoxy(naves[i].getPosicionX(),naves[i].getPosicionY());
        printf("%c",255);

        //Si estan en el tope de abajo
        if(naves[i].getPosicionY()<24&&naves[i].getPosicionY()!=1){
        //print y mover la nave uno hacia abajo
            naves[i].setPosicionY(naves[i].getPosicionY()+1);
            if(naves[i].getPosicionY()==24)
            {   if(vidas!=0)
                vidas--;
                if(vidas==0)
                {
                   break;
                }

            }
            gotoxy(naves[i].getPosicionX(),naves[i].getPosicionY());
            printf("%c",naves[i].getTipo());
        //comprobar si choca al moverse con Balas jugador

        }
        if(vidas==0)
        {
            break;
        }
    }

}
bool coliBalaJugYJug(vector<Balas> & balasJugador,vector<Naves>&naves,int &puntos)
{
    for(unsigned short int i=1;i<naves.size();i++)
    {   //SI ha colisionado el jugador con una nave enemiga
        if(naves[0].getPosicionX()==naves[i].getPosicionX()
           &&naves[0].getPosicionY()==naves[i].getPosicionY())
        {
            return true;
        }//Si ha colisionado una bala del jugador con una nave enemiga
        for(unsigned short int a=0;a<balasJugador.size();a++)
        {
            if(balasJugador[a].getPosicionX()==naves[i].getPosicionX()&&
                balasJugador[a].getPosicionY()==naves[i].getPosicionY()/*||
               balasJugador[a].getPosicionX()==naves[i].getPosicionX()
               &&balasJugador[a].getPosicionY()==naves[i].getPosicionY()+1*/)
            {
                gotoxy(balasJugador[a].getPosicionX(),balasJugador[a].getPosicionY());
                printf("%c",255);
                gotoxy(naves[i].getPosicionX(),naves[i].getPosicionY());
                printf("%c",255);
                balasJugador[a].setPosicionY(3);
                naves[i].setPosicionY(1);
                puntos++;
            }
        }
    }
}
void puntuacion(int puntAnte,int& puntos)
{
    color(0);
    //Si los puntos no han cambiado, no imprimo, flasea menos la pantalla
    if(puntos!=puntAnte)
    {
        for(unsigned short int i=0;i<8;i++)
        {
            gotoxy(52+i,21);
            printf("%c",255);
        }
            gotoxy(52,21);
            printf("%d",puntos);

    }
    color(1);
}
void vidas(unsigned short int& vidas)
{
    color(2);
    int barrita=vidas*2;
    for(unsigned short int i=0;i<6;i++)
    {
        gotoxy(52+i,17);
        printf("%c",255);
    }
    if (vidas>0)
    {
        for(unsigned short int i=0;i!=barrita;i++)
        {
            gotoxy(52+i,17);
            printf("%c",178);
        }
    }
    color(1);
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

    for(unsigned short int i=3;i!=25;i++)
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

    gotoxy(52,20);
    printf("Puntos");
    gotoxy(52,21);
    color(0);
    printf("0");
    color(1);
    gotoxy(52,16);
    printf("Vida");
    color(2);
    for(unsigned short int i=0;i<6;i++)
    {
        gotoxy(52+i,17);
        printf("%c",178);
    }
    color(1);
}
void gotoxy(int x, int y){
  COORD point;
  point.X = x; point.Y = y;
  SetConsoleCursorPosition(GetStdHandle(STD_OUTPUT_HANDLE), point);
}
void color(int num)
{
    switch (num){
        case 0:    // White on Black
        SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), FOREGROUND_INTENSITY | FOREGROUND_RED | FOREGROUND_GREEN | FOREGROUND_BLUE);
        break;
        case 1://cyan
        SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), FOREGROUND_INTENSITY | FOREGROUND_GREEN |FOREGROUND_BLUE);
        break;
        case 2://red
        SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE),FOREGROUND_INTENSITY | FOREGROUND_RED);
        break;
    }
}

bool teclaPresionada(int tecla) {
    return GetKeyState(tecla) < 0;
}
