# Prompt mestre refinado — engenharia, telemetria e manutenção

Analise o conjunto Springer Midea AirVolution identificado como 42AFVCI18S5 x 38TVCI18S5, 18.000 BTU/h, 220 V, 60 Hz, R32 e módulo EU-SK105. Use as fotos, manuais oficiais e medições anexadas ao repositório.

Separe todas as conclusões em quatro níveis: **CONFIRMADO** por foto ou fonte oficial; **PROVÁVEL** por coerência funcional; **HIPÓTESE** sem autorização para ligação; e **PRECISA MEDIR**.

## Perguntas técnicas obrigatórias

1. Quais sensores são visíveis, quais são esperados pelo manual e quais precisam ser adicionados externamente? Identifique ponto, tipo, unidade, frequência, calibração e risco.
2. Qual é a lógica de monitoramento e qual é a lógica de controle original? Preserve o controle Midea e trate o novo sistema como observação passiva.
3. Sem sensor de pressão, declare explicitamente que o superaquecimento e o sub-resfriamento termodinâmicos não podem ser calculados. Mostre as equações usando pressão–temperatura do R32 e diferencie cálculo real de indicador indireto.
4. Investigue somente fontes oficiais Midea para developer portal, SDK, API, SmartHome, parceiros, OAuth, webhooks, escopos, suporte e acesso. Se não houver SDK público, registre a ausência e proponha solicitação formal à Midea. Não trate Home Assistant, ESPHome ou bibliotecas comunitárias como SDK oficial.
5. Descreva quais painéis o SmartHome anuncia para usuário, alertas, monitoramento e energia, sem prometer recursos específicos do modelo que não estejam documentados.
6. Compare app próprio para gestores/técnicos com gateway ESP32/Arduino. Recomende uma arquitetura híbrida: SmartHome oficial para usuário, gateway passivo e dashboard próprio para manutenção, salvo quando uma API oficial for concedida.

## Entregáveis

Produza: (a) data sheet; (b) diagrama Mermaid de sensores, proteção, controle e telemetria; (c) matriz de conectores/chicotes com foto, serigrafia, pinos, cores, destino, função, medição, fonte e confiança; (d) modelo de dados MQTT/TLS; (e) regras de alarmes e tendências; (f) dashboard para usuário, gestor e técnico; (g) backlog priorizado com critérios de aceite; (h) plano de comissionamento; e (i) lista das próximas evidências.

Não invente pinagem, pressão, corrente, temperatura de descarga, API local, protocolo, limites de alarme ou causa raiz. Não implemente comando de escrita, acionamento de compressor, alteração de proteção, captura de credenciais pessoais ou engenharia reversa ativa sem documentação e autorização. Toda telemetria deve falhar para o estado seguro.
