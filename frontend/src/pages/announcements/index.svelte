<script lang="ts">
    import AnnouncementListElem from '../../lib/Announcements/AnnouncementListElem/AnnouncementListElem.svelte';
    import Footer from '../../lib/Footer/Footer.svelte';
    import Header from '../../lib/Header/Header.svelte';
    import SortFilterBanner from '../../lib/SortFilterBanner/SortFilterBanner.svelte';
    import execute from '../../lib/fetchWrapper';
    import { filteredCategoryIds, filteredLocationIds, sortingOption, nameSearchParam, clearFilters } from '../../lib/stores';

    let data = [];
    let selected: number | null = null;
    let sortOptions = [
        { id: 1, name: 'Po liczbie zapisanych rosnąco' },
        { id: 2, name: 'Po liczbie zapisanych malejąco' }
    ];

    clearFilters();

    const viewDetails = (postId) => {
        if (selected !== postId) {
            selected = postId;
        } else {
            selected = null;
        }
    };

    $: {
        let urlParams = new URLSearchParams();
        for (let categoryId of $filteredCategoryIds) {
            urlParams.append('categoryIds', categoryId);
        }
        for (let locationId of $filteredLocationIds) {
            urlParams.append('locationIds', locationId);
        }
        if ($sortingOption !== null) {
            urlParams.append('sortOption', $sortingOption);
        }
        if ($nameSearchParam !== null) {
            urlParams.append('nameSearch', $nameSearchParam);
        }

        console.log(urlParams.toString());

        execute('announcements?' + urlParams.toString(), 'GET')
            .then((r) => r.json())
            .then((r) => (data = r));
    }
</script>

<div class="h-screen">
    <Header />
    <SortFilterBanner {sortOptions} />
    <div class="h-[calc(100%-10rem)] lg:h-[calc(100%-14rem)] overflow-auto">
        {#each data as item}
            <AnnouncementListElem areDetailsShown={selected === item.id ? true : false} data={item} clickHandler={() => viewDetails(item.id)} />
        {/each}
    </div>
    <Footer pageType="announcements" />
</div>
