# Sensores e lógica de monitoramento e controle

## 1. Princípio

O controle da unidade permanece na eletrônica original. O novo sistema deve monitorar, registrar e diagnosticar sem assumir o papel do controlador. O aplicativo oficial é a interface de usuário; a interface de manutenção é uma camada separada e inicialmente somente leitura.

## 2. Sensores identificados ou esperados

| Grandeza | Sensor ou fonte | Estado no projeto | Uso |
|---|---|---|---|
| Temperatura do ambiente | Termistor interno da evaporadora ou sensor externo de referência | Provável; confirmar por manual e medição | Controle de conforto e comparação |
| Temperatura da serpentina evaporadora | Termistor de tubo/serpentina | Provável; confirmar por chicote | Proteção contra congelamento e diagnóstico |
| Temperatura de descarga | Sensor de contato externo ou termistor original, se existir | Precisa confirmar | Indício de sobreaquecimento do compressor |
| Temperatura do condensador | Sensor de contato externo ou termistor original, se existir | Precisa confirmar | Tendência de rejeição de calor |
| Umidade | Sensor externo | Opcional | Diagnóstico ambiental; não substitui controle original |
| Tensão | Transdutor isolado ou analisador de rede | Não presente nas fotos; instalar externamente | Qualidade de alimentação |
| Corrente/potência | TC, Hall ou medidor certificado | Não presente nas fotos; instalar externamente | Partida, carga e energia |
| Pressão de sucção/descarga | Manifold/transdutor de refrigeração | Não identificado | Necessário para superheat/subcooling real |
| Estado/código de falha | Display, aplicativo ou protocolo documentado | Parcialmente observável | Eventos e diagnóstico |

## 3. Superaquecimento e sub-resfriamento

Sem medir pressão, **não é possível calcular o superaquecimento e o sub-resfriamento termodinâmicos reais**. Temperatura de tubo sozinha não determina a temperatura de saturação do refrigerante.

As definições são:

```text
Superaquecimento = T_sucção_medida − T_saturação_evaporação(P_sucção)
Sub-resfriamento  = T_saturação_condensação(P_descarga) − T_líquido_medido
```

Para R32, a temperatura de saturação deve ser obtida da tabela ou equação pressão–temperatura apropriada para R32, usando a pressão medida no ponto correto. Não usar uma tabela de R410A, nem misturar temperatura de bolha e de orvalho sem especificar a convenção.

Se não houver sensor de pressão instalado, o projeto pode exibir somente **indicadores indiretos**: diferença entre temperaturas de entrada/saída, tendência de temperatura de descarga, tempo de estabilização, potência elétrica, temperatura ambiente e estado do ventilador. Esses indicadores não devem ser rotulados como superheat ou subcooling.

A alternativa segura para validar os valores é usar, durante o comissionamento, instrumentos de serviço apropriados e transdutores de pressão compatíveis com R32, instalados por profissional habilitado. Depois, registrar pressão e temperatura simultaneamente, com ponto, hora e condição de operação.

## 4. Lógica de monitoramento

O gateway coleta a cada 1–10 segundos os canais rápidos e grava agregados de 10–60 segundos. Ele classifica cada valor como `observed`, `derived`, `estimated` ou `invalid`. As regras de alerta usam limites do manual, dados de comissionamento e tendências; não usam limites genéricos como se fossem especificação Midea.

O diagnóstico correlaciona temperatura ambiente, setpoint, modo, estado dos ventiladores, tensão, corrente, temperaturas de tubo, conectividade e códigos de falha. O gateway não liga/desliga compressor, não altera válvula e não injeta sinal no barramento.

## 5. Lógica de controle

A recomendação é não duplicar o controle Midea. A unidade original mantém a máquina de estados, proteções, intertravamentos, sensores internos e acionamento. O novo sistema pode gerar alertas, relatórios e recomendações de inspeção. Qualquer comando remoto deve ser desabilitado na primeira versão.
