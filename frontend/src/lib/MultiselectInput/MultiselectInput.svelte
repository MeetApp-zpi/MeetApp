<script lang="ts">
    import Svelecte from 'svelecte';

    export let data;
    export let placeholder;
    export let inputId;
    export let selected;
    export let style;
</script>

<div class="svelecte-multiselect categorySvelecteControl categorySvelecteContent">
    <Svelecte {style} options={data} {placeholder} {inputId} multiple="true" bind:value={selected} />
</div>

<style>
    .svelecte-multiselect :global(.sv-content) {
        overflow-x: scroll;
    }

    .categorySvelecteControl :global(.sv-control) {
        border-color: var(--pickle);
        background-color: var(--ivory);
        border-width: 2px;
        padding: 0.5rem 0.5rem 0.5rem 0.5rem;
        color: var(--gray);
        border-radius: 0.5rem;
        font-size: 1.5rem;
        /* line-height: 2rem; */
    }

    .categorySvelecteContent :global(.inputBox) {
        height: 2rem;
        color: var(--gray);
    }
</style>
