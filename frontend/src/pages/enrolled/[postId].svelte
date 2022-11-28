<script lang="ts">
    import { redirect } from '@roxi/routify';

    import Header from '../../lib/Header/Header.svelte';
    import UserPill from '../../lib/UserPill/UserPill.svelte';
    import execute from '../../lib/fetchWrapper';

    export let postId: number;

    let users = [];
    let page: number = 0;

    let promise = execute(`users/isAuthor/${postId}`, 'GET')
        .then((r) => r.text())
        .then((r) => (r === 'false' ? $redirect('/') : null));

    const retrieveUsers = (page: number) => {
        execute(`enrollees/${postId}?page=${page}`, 'GET')
            .then((r) => r.json())
            .then((r) => (users = [...users, ...r]));
    };

    $: {
        retrieveUsers(page);
    }

    const infiniteScroll = () => {
        const usersContainer = document.getElementById('usersContainer');

        if (usersContainer.offsetHeight + usersContainer.scrollTop === usersContainer.scrollHeight) {
            page = page + 1;
        }
    };
</script>

<div class="h-screen">
    <Header />
    <div on:scroll={infiniteScroll} id="usersContainer">
        {#await promise then _}
            {#each users as user}
                <UserPill data={user} />
            {/each}
        {/await}
    </div>
</div>
