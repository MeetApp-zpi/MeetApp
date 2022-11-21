<script lang="ts">
    import { goto } from '@roxi/routify';

    import Header from '../../lib/Header/Header.svelte';
    import UserPill from '../../lib/UserPill/UserPill.svelte';
    import execute from '../../lib/fetchWrapper';

    export let postId: number;

    let users = [];

    let promise = execute(`users/isAuthor/${postId}`, 'GET')
        .then((r) => r.text())
        .then((r) => (r === 'false' ? $goto('/') : null));

    promise = promise.then((_) =>
        execute(`enrollees/${postId}`, 'GET')
            .then((r) => r.json())
            .then((r) => (users = r))
    );

    $: console.log(users);
</script>

<div class="h-screen">
    <Header />
    {#await promise then _}
        {#each users as user}
            <UserPill data={user} />
        {/each}
    {/await}
</div>
