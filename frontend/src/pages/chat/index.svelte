<script lang="ts">
    import { Client, Stomp } from '@stomp/stompjs';
    import SockJS from 'sockjs-client/dist/sockjs';

    const sock = new SockJS('http://localhost:8080/websockets');
    // const wsClient = new Client();

    const stomp = Stomp.over(sock);

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

    // // Create WebSocket connection.
    // const socket = new WebSocket('ws://localhost:5173/websockets');

    // // Connection opened
    // socket.addEventListener('open', (event) => {
    //     socket.send('Hello Server!');
    // });

    const sendMessage = () => {
        stomp.publish({ destination: '/ws/message/1', body: 'Hello STOMP' });
    };
</script>

<button class="h-10 w-10" on:click={sendMessage}>Send message!</button>
