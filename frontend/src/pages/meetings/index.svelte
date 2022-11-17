<script lang="ts">
    import MeetingListElem from '../../lib/Meetings/MeetingListElem.svelte';
    import Footer from '../../lib/Footer/Footer.svelte';
    import Header from '../../lib/Header/Header.svelte';
    import SortFilterBanner from '../../lib/SortFilterBanner/SortFilterBanner.svelte';
    import execute from '../../lib/fetchWrapper';

    let data = [];
    let selected: number | null = null;
    let sortOptions = [
        { id: 1, name: 'Sortuj po X' },
        { id: 2, name: 'Sortuj po Y' }
    ];

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
    <SortFilterBanner {sortOptions} />
    <div class="h-[calc(100%-10rem)] lg:h-[calc(100%-14rem)] overflow-auto">
        {#each data as item}
            <MeetingListElem areDetailsShown={selected === item.id ? true : false} data={item} clickHandler={() => viewDetails(item.id)} />
        {/each}
    </div>
    <Footer pageType="meetings" />
</div>
