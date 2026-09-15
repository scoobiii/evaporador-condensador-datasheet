# Midea DevOps, SDK e ferramentas de integração

## Conclusão atual

Não foi localizada uma documentação pública oficial da Midea Brasil que ofereça um SDK de manutenção, API aberta de telemetria, protocolo de placa ou painel para técnicos para este modelo específico. A Midea publica o aplicativo SmartHome e descreve controle, alertas, monitoramento e integrações de casa inteligente, mas isso não equivale a um SDK público para terceiros.

O caminho oficial para solicitar acesso é tratar a Midea/Grupo Midea Carrier como fornecedora e pedir, com o modelo e número de série, uma integração de parceiro ou documentação de serviço. O pedido deve solicitar formalmente: API/SDK aplicável ao Brasil, escopos, OAuth ou credenciais de serviço, rate limits, webhook, variáveis expostas, retenção, termos de uso, suporte ao EU-SK105 e acesso de gestor/técnico.

## Alternativas

| Opção | Oficialidade | Vantagem | Risco/limite | Recomendação |
|---|---|---|---|---|
| SmartHome oficial | Oficial | Usuário controla o equipamento e recebe recursos publicados pela Midea | Não garante dados internos de manutenção nem API pública | Usar para usuário e operação |
| API/SDK de parceiro Midea | Oficial, se concedido | Integração autorizada e estável | Depende de aprovação e contrato | Solicitar antes de engenharia reversa |
| Home Assistant + `midea_ac_lan` | Comunitária | Pode expor estados e sensores via rede local | Exige conta pessoal, tokens, pode quebrar e não é SDK oficial | Laboratório isolado, não base de garantia |
| ESPHome/Midea UART | Comunitária | Pode integrar um dongle e sensores | Requer hardware compatível, nível lógico de 5 V e protocolo não oficial | Somente bancada e sem potência |
| ESP32/Arduino com sensores externos | Projeto próprio | Não depende de API Midea para temperatura, corrente e vibração | Não lê automaticamente a placa; exige calibração e instalação segura | Melhor primeira solução de manutenção |

A integração comunitária `midea_ac_lan` informa que depende de conta pessoal SmartHome/Meiju e tokens do aparelho, e alerta que serviços de token podem ser descontinuados. Portanto, ela não deve ser tratada como API oficial ou como fundamento de operação crítica.

A documentação do ESPHome descreve uma interface Midea por UART a 9600 baud e alerta que o hardware exige níveis lógicos de 5 V; um ESP32 de 3,3 V não deve ser conectado diretamente sem adaptação. Isso é evidência de uma possibilidade comunitária, não confirmação da pinagem desta unidade.

## Dashboard oficial do app

O SmartHome oficial divulga cartão de controle, monitoramento em tempo real, alertas, cenas, consumo/insights e integrações como Google Home e Alexa, conforme região e produto. O conjunto exato de telas e métricas disponíveis para este modelo precisa ser verificado na conta e no aplicativo, pois a página de produto brasileira também contém uma observação de compatibilidade que deve ser conferida antes de prometer qualquer recurso.

## Referências

[1]: https://www.midea.com/global/smarthome "Midea SmartHome oficial"
[2]: https://www.midea.com/br/climatizacao/hiwall/ar-condicionado-springer-midea-airvolution-connect-18000-btu-h-frio.mobile.mobile.mobile "Página oficial brasileira do produto"
[3]: https://github.com/wuwentao/midea_ac_lan "Integração comunitária Midea AC LAN"
[4]: https://esphome.io/components/climate/midea/ "Componente comunitário ESPHome para Midea"
