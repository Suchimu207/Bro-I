import sys
from pygame import mixer

#TODO: Terminar de montar.

if __name__ == "__main__": #Boa prática. Executa somente se for diretamente (pelo arquivo em si).

    mixer.init()  
    #Recebendo os parâmetros.
    param1 = sys.argv[1]  # "dado1"
    param2 = int(sys.argv[2])  # 42
    
    if (tipoSom == "Música"):
    try:
        som = mixer.Sound("./snd/sndLog.wav")
        mixer.Sound.play(som)        
    except Exception:
        return
    
    if (tipoSom == "Música"):
    try:
	mixer.music.load("./mus/mus_end.ogg")
        if tipoMúsica == "créditos" ou tipoMúsica == "ambiente":
            mixer.music.play(loops=-1)
            if tipoMúsica == "batalha":
                mixer.music.queue("./mus/mus_battle1_loop.ogg", "ogg", -1) #Loop.
                mixer.music.play()
            if tipoMúsica == "vitória":
                mixer.music.play()
        if tipoMúsica == "parada":
            mixer.music.fadeout(1)
    except Exception:
        mixer.music.fadeout(1)