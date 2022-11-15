<script lang="ts">
    import AddPostButton from '../../lib/AddPostButton/AddPostButton.svelte';
    import MeetingListElem from '../../lib/Meetings/MeetingListElem.svelte';
    import Footer from '../../lib/Footer/Footer.svelte';
    import Header from '../../lib/Header/Header.svelte';
    import execute from '../../lib/fetchWrapper';

    let data = [];
    let selected: number | null = null;

    execute('meetings', 'GET')
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
            <MeetingListElem areDetailsShown={selected === item.id ? true : false} data={item} clickHandler={() => viewDetails(item.id)} />
        {/each}
    </div>
    <AddPostButton pageType="meetings" />
    <Footer pageType="meetings" />
</div>
