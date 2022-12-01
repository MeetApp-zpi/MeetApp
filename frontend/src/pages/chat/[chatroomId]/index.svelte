<script lang="ts">
    import io from 'socket.io-client';
    import { goto } from '@roxi/routify';
    import { onMount } from 'svelte';

    import execute from '../../../lib/fetchWrapper';

    export let chatroomId: number;

    let chatInputValue;
    let chatMessages = [];
    let page: number = 0;
    let messagesContainer;
    let isScrolled: boolean = false;

    let promise = execute(`chatrooms/existsById/${chatroomId}`, 'GET')
        .then((r) => (r.status === 500 ? $goto('/') : r.json()))
        .then((r) => (r === false ? $goto('/') : null))
        .catch((err) => $goto('/'));

    const retrieveMessages = (pageNum: number) => {
        execute(`messages/${chatroomId}?page=${pageNum}`, 'GET')
            .then((r) => r.json())
            .then((r) => {
                r.reverse();
                chatMessages = [...r, ...chatMessages];
            });
    };

    const scrollToBottom = (node) => {
        node.scroll({
            top: node.scrollHeight,
            behavior: 'smooth'
        });
    };

    const infiniteScroll = () => {
        const messagesContainer = document.getElementById('messagesContainer');
        if (messagesContainer.scrollTop === 0) {
            page = page + 1;
        }
    };

    const updateScroll = () => {
        setTimeout(() => scrollToBottom(messagesContainer), 1000);
    };

    onMount(() => {
        updateScroll();
    });

    const socket = io('http://localhost:3000', {
        path: '/websockets',
        autoConnect: false
    });

    socket.auth = { chatroomId };

    socket.connect();

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
        });
    };

    $: {
        retrieveMessages(page);
    }

    const handleKeyDown = (e) => {
        if (e.key === 'Enter') {
            sendMessage();
            chatInputValue = '';
            updateScroll();
        }
    };

    socket.on('priv msg', ({ content, from }) => {
        chatMessages = [...chatMessages, content];
    });
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
