import sys
from pygame import mixer

#TODO: Terminar de montar.

if __name__ == "__main__": #Boa pr�tica. Executa somente se for diretamente (pelo arquivo em si).

    mixer.init()  
    #Recebendo os par�metros.
    param1 = sys.argv[1]  # "dado1"
    param2 = int(sys.argv[2])  # 42
    
    if (tipoSom == "M�sica"):
    try:
        som = mixer.Sound("./snd/sndLog.wav")
        mixer.Sound.play(som)        
    except Exception:
        return
    
    if (tipoSom == "M�sica"):
    try:
	mixer.music.load("./mus/mus_end.ogg")
        if tipoM�sica == "cr�ditos" ou tipoM�sica == "ambiente":
            mixer.music.play(loops=-1)
            if tipoM�sica == "batalha":
                mixer.music.queue("./mus/mus_battle1_loop.ogg", "ogg", -1) #Loop.
                mixer.music.play()
            if tipoM�sica == "vit�ria":
                mixer.music.play()
        if tipoM�sica == "parada":
            mixer.music.fadeout(1)
    except Exception:
        mixer.music.fadeout(1)