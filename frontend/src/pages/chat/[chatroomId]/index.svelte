<script lang="ts">
    import io from 'socket.io-client';
    import { goto, node, redirect } from '@roxi/routify';
    import { tick } from 'svelte';
    import execute from '../../../lib/fetchWrapper';
    import Header from '../../../lib/Header/Header.svelte';
    import MdSend from 'svelte-icons/md/MdSend.svelte';
    import { autoresize } from 'svelte-textarea-autoresize';
    import { userDetails, haveUnreadMessage } from '../../../lib/stores.js';

    export let chatroomId: number;

    let chatInputValue: string = '';
    let chatInput;

    let chatMessages = [];
    let page: number = 0;
    let messagesContainer;
    let tickCalled: number = 0;
    let previousScrollHeight: number = 0;

    execute(`chatrooms/markAsRead/${chatroomId}`, 'GET').then((_) =>
        execute('chatrooms/haveUnreadMessage', 'GET')
            .then((r) => r.json())
            .then((r) => {
                $haveUnreadMessage = r;
            })
    );

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
            })
            .then((_) => scrollAfterLoading());
    };

    const infiniteScroll = () => {
        const messagesContainer = document.getElementById('messagesContainer');
        if (messagesContainer.scrollTop === 0) {
            console.log('called');
            page = page + 1;
        }
    };

    const scrollAfterLoading = async () => {
        await tick();
        if (messagesContainer !== undefined) {
            messagesContainer.scroll({
                top: messagesContainer.scrollHeight - previousScrollHeight
            });
            console.log(messagesContainer.scrollHeight, messagesContainer.offsetHeight, messagesContainer.scrollTop);
            previousScrollHeight = messagesContainer.scrollHeight;
        }
    };

    $: {
        retrieveMessages(page);
    }

    const scrollForViewers = (node) => {
        if (node !== null && (node.scrollHeight - (node.scrollTop + node.offsetHeight) < 50 || tickCalled === 2)) {
            node.scroll({
                top: node.scrollHeight,
                behavior: 'smooth'
            });
        }
    };

    const updateScrollViewer = async () => {
        await tick();
        tickCalled += 1;
        scrollForViewers(messagesContainer);
    };

    const updateScrollAuthor = async () => {
        await tick();
        tickCalled += 1;
        messagesContainer.scroll({
            top: messagesContainer.scrollHeight,
            behavior: 'smooth'
        });
    };

    const socket = io('http://localhost:3000', {
        path: '/websockets',
        autoConnect: false
    });

    socket.auth = { chatroomId };

    socket.connect();

    socket.on('priv msg', ({ content, from }) => {
        chatMessages = [...chatMessages, content];
        updateScrollViewer();
        execute(`chatrooms/markAsRead/${chatroomId}`, 'GET');
    });

    const sendMessage = () => {
        if (chatInputValue.length > 0) {
            execute(`messages/${chatroomId}`, 'POST', chatInputValue).then((r) => (r.status === 201 ? addNewMessage(r.json()) : null));
        }

        chatInputValue = '';
        chatInput.style.setProperty('height', `24px`, 'important');
    };

    const addNewMessage = async (msg) => {
        msg.then((r) => {
            chatMessages = [...chatMessages, r];
            socket.emit('priv msg', {
                content: r,
                to: chatroomId
            });
            execute(`chatrooms/markAsUnread/${chatroomId}`, 'GET');
        }).then(async () => await updateScrollAuthor());
    };

    const handleKeyDown = (e) => {
        if (e.key === 'Enter') {
            e.preventDefault();
            sendMessage();
        }
    };

    const fetchPartner = () => {
        return execute(`chatrooms/clientOf/${chatroomId}`, 'GET')
            .then(async (r) => (r.status !== 200 ? $goto('/login') : await r.json()))
            .catch((err) => $goto('/login'));
    };
</script>

<svelte:head>
    <script src="https://cdn.socket.io/4.5.4/socket.io.min.js"></script>
</svelte:head>

<div class="h-screen">
    <Header />
    <div class="flex flex-col h-[calc(100%-4rem)] lg:h-[calc(100%-4rem)] lg:w-1/3 lg:mx-auto">
        {#await fetchPartner() then partner}
            <div
                class="h-16 mx-2 mt-2 flex-none"
                on:click={() => $redirect(`/profile/${partner.id}`)}
                on:keydown={() => $redirect(`/profile/${partner.id}`)}
            >
                <div class="rounded-2xl py-2 justify-around flex flex-row bg-olive text-cocoa items-center">
                    <div class="h-full w-12">
                        <img class="rounded-full" src={partner.profilePicture} alt="User avatar" referrerpolicy="no-referrer" />
                    </div>
                    <div class="flex flex-col">
                        <div class="flex-1 w-52 font-bold">{partner.firstName} {partner.lastName}</div>
                        <div class="flex-1 w-52 overflow-auto">{partner.email}</div>
                    </div>
                </div>
            </div>
        {/await}
        <div class="flex-auto overflow-auto mx-5 mt-5" on:scroll={infiniteScroll} id="messagesContainer" bind:this={messagesContainer}>
            {#await promise then _}
                {#each chatMessages as chatMessage}
                    <div class="flex flex-row justify-between">
                        {#if chatMessage.author.id === $userDetails.id}
                            <div class="flex w-1/3" />
                            <div class="bg-grass rounded-xl mb-4 py-1 px-3" style="word-break: break-word">{chatMessage.content}</div>
                        {:else}
                            <div class="bg-orange bg-opacity-75 rounded-xl mb-4 py-1 px-3">{chatMessage.content}</div>
                            <div class="flex w-1/3" />
                        {/if}
                    </div>
                {/each}
            {/await}
        </div>

        <form class="flex flex-row bg-tea py-3 px-3 my-5 mx-5 rounded-2xl justify-between" on:submit={(e) => e.preventDefault()}>
            <textarea
                use:autoresize
                class="resize-none max-h-[6rem] bg-tea border-none outline-none pr-4 placeholder-sage flex-1"
                placeholder="Type your message here"
                bind:value={chatInputValue}
                on:keydown={handleKeyDown}
                bind:this={chatInput}
            />

            <button type="submit" class="flex self-center h-6 w-6" on:click={sendMessage}>
                <MdSend />
            </button>
        </form>
    </div>
</div>
