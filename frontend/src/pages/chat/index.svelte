<script lang="ts">
    import { Client, Stomp } from '@stomp/stompjs';
    import SockJS from 'sockjs-client/dist/sockjs';

    // const sock = new SockJS('http://localhost:8080/websockets');
    // const wsClient = new Client();

    // const stomp = Stomp.client('wss://192.168.0.66:8080/websockets');

    // const stomp = Stomp.over(() => new WebSocket('ws://localhost:8080/websockets'));
    const stomp = Stomp.over(() => new SockJS('http://localhost:8080/websockets'));

    stomp.activate();

    // stomp.onConnect = () => {
    //     console.log(stomp.active, stomp);
    //     stomp.subscribe('/user/queue/reply', (message) => {
    //         console.log('Message: ' + message.body);
    //     });

    //     stomp.subscribe('/user/queue/errors', (message) => {
    //         console.log('Errors: ' + message.body);
    //     });
    // };

    const sendMessage = () => {
        stomp.publish({ destination: '/ws/message/1', body: 'Hello STOMP' });
    };
</script>

<button class="h-10 w-10" on:click={sendMessage}>Send message!</button>
