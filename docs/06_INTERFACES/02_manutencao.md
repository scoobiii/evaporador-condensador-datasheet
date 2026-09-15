# Interface — manutenção

A interface de manutenção será somente leitura na primeira fase. Ela deve apresentar identificação, estado operacional, eventos, falhas, dados de sensores validados, ciclos, comunicação entre unidades quando documentada, histórico, tendências, evidências de medição e nível de confiança.

| Grupo | Exemplos | Qualidade mínima |
|---|---|---|
| Identificação | Modelo, série, módulo Wi‑Fi, versão conhecida | Confirmado por etiqueta ou fonte oficial |
| Operação | Ligado, modo, setpoint, ventilador, timer | Observado por interface documentada |
| Eventos | Partida, parada, perda de rede, código de falha | Timestamp e origem |
| Sensores | Ambiente, evaporador, condensador, descarga | Sensor e calibração documentados |
| Energia | Tensão e corrente | Medidor externo isolado ou canal documentado |
| Diagnóstico | Tendências e correlações | Derivado, com fórmula e dados de entrada |

Comandos de escrita e acionamento ficam fora do escopo até haver documentação, pinagem validada e teste controlado.
