import { writable } from 'svelte/store';

export let filteredCategoryIds = writable([]);
export let filteredLocationIds = writable([]);
export let sortingOption = writable(null);
export let nameSearchParam = writable(null);

export function clearFilters() {
    filteredCategoryIds.set([]);
    filteredLocationIds.set([]);
    sortingOption.set(null);
    nameSearchParam.set(null);
}
