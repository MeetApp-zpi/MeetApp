<script lang="ts">
    import io from 'socket.io-client';
    import { goto } from '@roxi/routify';
    import execute from '../../../lib/fetchWrapper';

    export let chatroomId: number;

    let chatInputValue;
    let chatMessages = [];

    let promise = execute(`chatrooms/existsById/${chatroomId}`, 'GET')
        .then((r) => (r.status === 500 ? $goto('/') : r.json()))
        .then((r) => (r === false ? $goto('/') : null))
        .catch((err) => $goto('/'));

    promise = promise.then((_) => execute(`chatrooms`));

    const socket = io('http://localhost:3000', {
        path: '/websockets',
        autoConnect: false
    });

    socket.auth = { chatroomId };

    socket.connect();

    const sendMessage = () => {
        if (chatInputValue.length > 0) {
            socket.emit('priv msg', {
                content: chatInputValue,
                to: chatroomId
            });
        }
    };

    socket.on('priv msg', ({ content, from }) => {
        console.log(from, content);
    });
</script>

<svelte:head>
    <script src="https://cdn.socket.io/4.5.4/socket.io.min.js"></script>
</svelte:head>

{#await promise then _}
    <input type="text" bind:value={chatInputValue} />
    <button class="h-10 w-10" on:click={sendMessage}>Send message!</button>
{/await}
