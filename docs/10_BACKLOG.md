# Backlog refinado — plataforma de manutenção

## Decisão arquitetural recomendada

Construir uma solução híbrida: manter o aplicativo SmartHome oficial para o usuário final e criar uma plataforma própria de manutenção baseada em gateway ESP32/Arduino, sensores externos certificados, MQTT/TLS, banco de séries temporais e dashboard web. A integração oficial Midea deve ser adicionada somente se a Midea conceder API/SDK documentado.

O Arduino/ESP32 não deve comandar a placa de potência na primeira versão. Ele coleta sensores externos, códigos/estados somente quando a interface estiver documentada e saúde da conectividade. O backend calcula tendências, abre ordens de inspeção e apresenta evidências.

## Épicos e critérios de aceite

| ID | Épico | Entrega | Critério de aceite | Prioridade |
|---|---|---|---|---:|
| E1 | Identidade do ativo | Cadastro de modelo, série, local e fotos | Cada ativo tem identidade e trilha de evidência | P0 |
| E2 | Inventário elétrico | IDs de conectores, chicotes e fotos | Nenhuma função é marcada confirmada sem fonte | P0 |
| E3 | Gateway passivo | ESP32/Arduino, sensores externos e isolamento | Perda do gateway não altera o HVAC | P0 |
| E4 | Aquisição ambiental | Temperatura, umidade e estado | Valores têm unidade, timestamp e calibração | P0 |
| E5 | Energia | Tensão, corrente e potência externa | Medição certificada e sem abrir circuito indevidamente | P1 |
| E6 | Refrigerante | Pressão e temperatura para superheat/subcooling | Apenas transdutor/manifold compatível com R32 e técnico habilitado | P1 |
| E7 | Telemetria | MQTT/TLS, fila local e retenção | Funciona por 24 h sem rede e sincroniza depois | P0 |
| E8 | Dashboard técnico | Ativos, alarmes, tendências e evidências | Gestor filtra por local, prioridade e estado | P0 |
| E9 | App de técnico | Checklists, fotos, medições e assinatura interna | Ordem de serviço preserva auditoria e qualidade | P1 |
| E10 | SmartHome oficial | Integração autorizada, se disponível | Sem credencial pessoal embutida no backend | P2 |
| E11 | Diagnóstico | Regras de tendência e nível de confiança | Recomendações não são apresentadas como causa confirmada | P1 |
| E12 | Segurança | RBAC, TLS, rotação de credenciais e logs | Usuário, gestor e técnico têm escopos distintos | P0 |

## Milestones

### M0 — evidência e escopo

Concluir fotos, etiquetas, inventário de conectores e pedido formal à Midea. Entregável: pacote de evidências e matriz confirmado/provável/hipótese/precisa medir.

### M1 — protótipo de bancada

Montar ESP32/Arduino sem conexão à potência, com sensores de temperatura, umidade e energia em baixa tensão. Publicar dados simulados e reais em MQTT/TLS. Entregável: firmware, esquema de ligação isolado e teste de perda de rede.

### M2 — comissionamento controlado

Comparar sensores com instrumentos calibrados e registrar condições. Se necessário, adicionar transdutores de pressão compatíveis com R32 somente por técnico habilitado. Entregável: relatório de calibração e cálculo documentado.

### M3 — dashboard MVP

Implementar cadastro de ativos, visão do gestor, visão do técnico, histórico, alertas, anexos de fotos e exportação CSV. Entregável: aplicação sem comandos de acionamento.

### M4 — integração oficial

Somente após resposta da Midea: implementar OAuth/API/SDK documentado, respeitar escopos e criar testes de contrato. Sem resposta, manter solução externa passiva.

## Fora do escopo da primeira versão

Não inclui injeção no barramento, controle de compressor, alteração de parâmetros de proteção, descoberta de senha/token, captura de credenciais pessoais, cálculo de carga de refrigerante por inferência ou declaração de superheat/subcooling sem pressão.
