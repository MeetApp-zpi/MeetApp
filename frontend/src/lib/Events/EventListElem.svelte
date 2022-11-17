<script lang="ts">
    import FaCalendarAlt from 'svelte-icons/fa/FaCalendarAlt.svelte';
    import FaMapMarkedAlt from 'svelte-icons/fa/FaMapMarkedAlt.svelte';
    import MdPeople from 'svelte-icons/md/MdPeople.svelte';

    export let data;
    export let clickHandler: () => void;
</script>

<div class="bg-olive rounded-2xl m-2 p-2">
    <div class="flex flex-row hover:cursor-pointer">
        <div class="w-1/3 self-center">
            {#if data.picture !== null}
                <img class="w-full" src="http://localhost:8080/{data.picture}" alt="Event poster" />
            {:else}
                <img class="w-full" src="no-image.png" alt="Missing poster" />
            {/if}
        </div>
        <div class="flex flex-col w-2/3 pl-2">
            <button on:click={clickHandler}>
                <div class="font-bold text-left">
                    {data.title}
                </div>
                <div class="flex flex-row">
                    <div class="w-6 mx-2">
                        <FaMapMarkedAlt />
                    </div>
                    {data.location.city.name}
                </div>
                <div class="flex flex-row">
                    <div class="w-6 mx-2">
                        <FaCalendarAlt />
                    </div>
                    {data.startDateTime.date}
                </div>
                <div class="flex flex-row">
                    <div class="w-6 mx-2">
                        <MdPeople />
                    </div>
                    <div class="text-orange">{data.enrolled} &nbsp;</div>
                    <div>{data.personQuota !== null ? '/ ' + data.personQuota : ''}</div>
                </div>
            </button>
        </div>
    </div>
</div>
