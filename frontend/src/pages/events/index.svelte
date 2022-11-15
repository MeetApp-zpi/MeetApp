<script lang="ts">
    import { redirect } from '@roxi/routify';

    import EventListElem from '../../lib/Events/EventListElem.svelte';
    import Footer from '../../lib/Footer/Footer.svelte';
    import Header from '../../lib/Header/Header.svelte';
    import execute from '../../lib/fetchWrapper';

    let data = [];
    let selected: number | null = null;

    execute('events', 'GET')
        .then((r) => r.json())
        .then((r) => (data = r));

    const viewDetails = (postId) => {
        $redirect(`/events/${postId}`);
    };
</script>

<div class="h-screen">
    <Header />
    <div class="h-[calc(100%-8rem)] lg:h-[calc(100%-12rem)] overflow-auto">
        {#each data as item}
            <EventListElem data={item} clickHandler={() => viewDetails(item.id)} />
        {/each}
    </div>
    <Footer pageType="events" />
</div>
