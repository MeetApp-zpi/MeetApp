<script lang="ts">
    import AnnouncementListElem from '../../lib/Announcements/AnnouncementListElem/AnnouncementListElem.svelte';
    import Footer from '../../lib/Footer/Footer.svelte';
    import Header from '../../lib/Header/Header.svelte';
    import execute from '../../lib/fetchWrapper';

    let data = [];
    let selected: number | null = null;

    execute('announcements', 'GET')
        .then((r) => r.json())
        .then((r) => (data = r));

    const viewDetails = (postId) => {
        if (selected !== postId) {
            selected = postId;
        } else {
            selected = null;
        }
    };
</script>

<div class="h-screen">
    <Header />
    <div class="h-[calc(100%-8rem)] lg:h-[calc(100%-12rem)] overflow-auto">
        {#each data as item}
            <!-- svelte-ignore a11y-click-events-have-key-events -->
            <div on:click={() => viewDetails(item.id)}>
                <AnnouncementListElem areDetailsShown={selected === item.id ? true : false} data={item} />
            </div>
        {/each}
    </div>
    <Footer pageType="announcements" />
</div>
