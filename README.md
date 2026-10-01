Simulador de Tráfego 2D

É um simulador de trânsito desenvolvido em Java, utiliza programação concorrente com múltiplas threads para cada carro e mutex para os semáforos.

Como funciona:
- Trânsito contínuo: Os veículos são gerados automaticamente em pistas que contêm duas faixas.
- Thread: Cada carro roda na sua própria Thread, possui velocidade única e pode seguir reto ou virar no cruzamento.
- Semáforo: Os carros respeitam os semáforos e utilizam um sistema de radar para não baterem no veículo da frente.
- Interface Gráfica: Exibe o tráfego e a mudança de luzes dos semáforos em tempo real.

Funcionalidades Implementadas
- Paralelismo: Cada veículo é instanciado e executado em uma Thread separada, com velocidades independentes.
- Exclusão Mútua: Utilização de "java.util.concurrent.Semaphore" para controlar o acesso à zona central do cruzamento, evitando colisões.
- Lógica de Tráfego Avançada: 
   - Duas faixas de rolagem no mesmo sentido em cada pista.
   - Sistema de detecção de distância (radar) para evitar engavetamentos na fila do semáforo.
   - Capacidade dos veículos de seguir em linha reta ou realizar curvas organicamente no cruzamento.
- Feedback Visual: Interface gráfica construída do zero renderizada a ~60 FPS, com semáforos visuais sincronizados com o comportamento das Threads dos carros.

- Programa utilizado: Eclipse
- 
