<script lang="ts">
    import io from 'socket.io-client';
    import { goto } from '@roxi/routify';

    import execute from '../../lib/fetchWrapper';

    // execute('chatrooms/exists/fanatyk.rolkarstwa@rolki.pl', 'GET')
    //     .then((r) => (r.status === 500 ? $goto('/') : r.json()))
    //     .then((r) => console.log(r))
    //     .catch((err) => $goto('/'));

    const socket = io('http://localhost:3000', {
        path: '/websockets',
        autoConnect: false
    });

    socket.onAny((event, ...args) => {
        console.log(event, args);
    });

    socket.connect();

    const sendMessage = () => {
        socket.emit('priv msg', {
            content: 'bajo jajo',
            to: 'room1'
        });
    };

    socket.on('priv msg', ({ content, from }) => {
        console.log(from, content);
    });
</script>

<svelte:head>
    <script src="https://cdn.socket.io/4.5.4/socket.io.min.js"></script>
</svelte:head>

<button class="h-10 w-10" on:click={sendMessage}>Send message!</button>
