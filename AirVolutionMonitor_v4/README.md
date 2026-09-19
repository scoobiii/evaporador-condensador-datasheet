# AirVolution Monitor v4

Aplicativo Android para monitoramento somente leitura do AirVolution via gateway M-Smart/LAN. A versão v4 adiciona presença física por BLE: ao detectar o beacon instalado próximo à máquina, o app autentica automaticamente no gateway local e passa a atualizar a telemetria a cada 3 segundos. Ao sair do alcance, a sessão é encerrada e os valores são removidos da tela.

## Requisitos

- Um beacon Bluetooth Low Energy instalado próximo à evaporadora. O MAC do beacon é configurado no app.
- Um gateway Python na mesma LAN do aparelho, executando `midea-local==12.0.0` e acessível pela URL configurada.
- IP do aparelho Midea e Token/Key válidos para o protocolo local. O gateway não deve ser exposto à internet.
- Android 8.0 ou superior. Em Android 12+, a primeira execução solicita permissão de Bluetooth.

## Segurança

Token e Key são cifrados no Android Keystore. O app envia essas credenciais somente ao gateway local durante a conexão. O MAC e o limiar de RSSI são usados apenas para determinar presença. Nenhum comando de controle é enviado à máquina.

## Configuração rápida

1. Instale e inicie o gateway em uma máquina que alcance o ar-condicionado na LAN: `uvicorn main:app --host 0.0.0.0 --port 8000`.
2. No app, abra **Configurar**, informe URL do gateway, IP do aparelho, Token, Key, MAC do beacon e o RSSI mínimo. Um valor inicial razoável é `-75 dBm`; ajuste no local.
3. Conceda a permissão de proximidade Bluetooth e salve.
4. Aproxime-se da máquina. Após dois anúncios BLE acima do limiar, o app autentica e inicia a leitura. Após quatro leituras abaixo do limiar, a sessão é encerrada.

## Limitações importantes

O modelo AirVolution documenta T1/T2/T3 e códigos de diagnóstico, mas a disponibilidade de cada atributo depende do módulo e firmware. O app exibe somente os campos realmente retornados pelo gateway; não transforma previsão em medição e não chama indicadores indiretos de superheat/subcooling.

O projeto não assume que o módulo Wi-Fi Midea ofereça proximidade Bluetooth. Por isso, o beacon BLE é explícito e configurável. Sem beacon, a autenticação automática por aproximação não pode ser garantida.

## Build

Abra em Android Studio e execute o build do módulo `app`. Este pacote contém código-fonte; o ambiente de origem não inclui Android SDK/Gradle Wrapper para gerar um APK aqui.
