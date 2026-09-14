# Prompt refinado de análise

Analise todas as fotos e documentos do conjunto split Midea identificado como GPA CD1 1965 RH. Produza um data sheet reverso preliminar da evaporadora e da condensadora, separando rigorosamente fatos legíveis, inferências funcionais, hipóteses e itens que precisam ser medidos.

Identifique fabricante, modelo, códigos de placa, conectores, serigrafia, sensores, atuadores, barramento entre unidades, módulo Wi‑Fi e proteções. Não invente pinagem, tensão, corrente, pressão, protocolo ou limite de alarme.

Monte um diagrama Mermaid da arquitetura da lógica com sensores de temperatura, pressão quando existente, proteções originais, MCU, placa de potência, compressor, ventiladores, válvula, interface/display, módulo Wi‑Fi e gateway de manutenção. Separe controle original de aquisição passiva.

Documente a extração e o monitoramento via Wi‑Fi para o usuário e para a manutenção. Inclua modelo de dados com timestamp, unidade, origem e qualidade; MQTT sobre TLS ou API documentada; fila local durante perda de rede; histórico; dashboards; alertas; retenção; segurança; calibração; comissionamento; diagnóstico de sensores, ventiladores, compressor, tensão, temperatura, comunicação e códigos de falha.

Diferencie valores observados, derivados e estimados. Mantenha comandos remotos desabilitados por padrão e preserve todas as proteções originais. Escreva em português técnico, com tabelas, limitações, riscos elétricos e uma lista objetiva das próximas fotos e medições necessárias para converter o esquema preliminar em pinagem validada.
