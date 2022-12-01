<script lang="ts">
    import io from 'socket.io-client';
    import { goto } from '@roxi/routify';
    import { tick } from 'svelte';
    import execute from '../../../lib/fetchWrapper';

    export let chatroomId: number;

    let chatInputValue;
    let chatMessages = [];
    let page: number = 1;
    let messagesContainer;
    let tickCalled: number = 0;

    let promise = execute(`chatrooms/existsById/${chatroomId}`, 'GET')
        .then((r) => (r.status === 500 ? $goto('/') : r.json()))
        .then((r) => (r === false ? $goto('/') : null))
        .catch((err) => $goto('/'));

    const retrieveMessages = (pageNum: number) => {
        execute(`messages/${chatroomId}?page=${pageNum}`, 'GET')
            .then((r) => r.json())
            .then((r) => {
                r.reverse();
                if (r.length > 0) {
                    chatMessages = [...r, ...chatMessages];
                }
            });
    };

    const infiniteScroll = () => {
        const messagesContainer = document.getElementById('messagesContainer');
        if (messagesContainer.scrollTop === 0) {
            console.log('called');
            page = page + 1;
        }
    };

    $: {
        retrieveMessages(page);
    }

    const scrollToBottom = (node) => {
        if (node !== null && (node.scrollHeight - (node.scrollTop + node.offsetHeight) < 50 || tickCalled === 2)) {
            node.scroll({
                top: node.scrollHeight,
                behavior: 'smooth'
            });
        }
    };

    const updateScroll = async () => {
        await tick();
        tickCalled += 1;
        scrollToBottom(messagesContainer);
    };

    $: {
        chatMessages;
        updateScroll();
    }

    const socket = io('http://localhost:3000', {
        path: '/websockets',
        autoConnect: false
    });

    socket.auth = { chatroomId };

    socket.connect();

    socket.on('priv msg', ({ content, from }) => {
        chatMessages = [...chatMessages, content];
        updateScroll();
    });

    const sendMessage = () => {
        if (chatInputValue.length > 0) {
            execute(`messages/${chatroomId}`, 'POST', chatInputValue).then((r) => (r.status === 201 ? addNewMessage(r.json()) : null));
        }
    };

    const addNewMessage = async (msg) => {
        msg.then((r) => {
            chatMessages = [...chatMessages, r];
            socket.emit('priv msg', {
                content: r,
                to: chatroomId
            });
        }).then(async () => await updateScroll());
    };

    const handleKeyDown = (e) => {
        if (e.key === 'Enter') {
            sendMessage();
            chatInputValue = '';
        }
    };
</script>

<svelte:head>
    <script src="https://cdn.socket.io/4.5.4/socket.io.min.js"></script>
</svelte:head>

<div class="h-40 overflow-auto" on:scroll={infiniteScroll} id="messagesContainer" bind:this={messagesContainer}>
    {#await promise then _}
        {#each chatMessages as chatMessage}
            <div class="h-6">{chatMessage.content}</div>
        {/each}
    {/await}
</div>
<input type="text" bind:value={chatInputValue} on:keydown={handleKeyDown} />
<button class="h-10 w-10" on:click={sendMessage}>Send message!</button>
