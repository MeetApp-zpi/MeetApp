<script lang="ts">
    import AnnouncementListElem from '../../lib/Announcements/AnnouncementListElem/AnnouncementListElem.svelte';
    import Footer from '../../lib/Footer/Footer.svelte';
    import Header from '../../lib/Header/Header.svelte';
    import SortFilterBanner from '../../lib/SortFilterBanner/SortFilterBanner.svelte';
    import execute from '../../lib/fetchWrapper';

    let data = [];
    let selected: number | null = null;
    let sortOptions = [
        { id: 1, name: 'Po liczbie zapisanych rosnąco' },
        { id: 2, name: 'Po liczbie zapisanych malejąco' }
    ];

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

    const setData = (newData) => {
        data = newData;
    };
</script>

<div class="h-screen">
    <Header />
    <SortFilterBanner {sortOptions} {setData} pageType="announcements" />
    <div class="h-[calc(100%-10rem)] lg:h-[calc(100%-14rem)] overflow-auto">
        {#each data as item}
            <AnnouncementListElem areDetailsShown={selected === item.id ? true : false} data={item} clickHandler={() => viewDetails(item.id)} />
        {/each}
    </div>
    <Footer pageType="announcements" />
</div>
