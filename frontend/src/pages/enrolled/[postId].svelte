<script lang="ts">
    import { goto } from '@roxi/routify';

    import Header from '../../lib/Header/Header.svelte';
    import execute from '../../lib/fetchWrapper';

    export let postId: number;

    execute(`users/isAuthor/${postId}`, 'GET')
        .then((r) => r.text())
        .then((r) => (r === 'false' ? $goto('/') : null));
</script>

<div class="h-screen">
    <Header />
</div>
