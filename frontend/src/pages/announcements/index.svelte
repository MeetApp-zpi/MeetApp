<script lang="ts">
    import AddPostButton from '../../lib/AddPostButton/AddPostButton.svelte';
    import AnnouncementListElem from '../../lib/Announcements/AnnouncementListElem/AnnouncementListElem.svelte';
    import Footer from '../../lib/Footer/Footer.svelte';
    import Header from '../../lib/Header/Header.svelte';
    import execute from '../../lib/fetchWrapper';

    let data = [];
    let selected: number | null = null;

    let promise = execute('announcements', 'GET')
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
        {#await promise then _}
            {#each data as item}
                <AnnouncementListElem areDetailsShown={selected === item.id ? true : false} data={item} clickHandler={() => viewDetails(item.id)} />
            {/each}
        {/await}
    </div>
    <AddPostButton pageType="announcements" />
    <Footer pageType="announcements" />
</div>
